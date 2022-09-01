from urllib import parse
from lxml import etree

import requests
import json

from jieba.analyse import extract_tags
from pyquery import PyQuery as pq
from dao.OpiDao import OpiDao
from config import user_agent_list, questionlist, questionurl1, questionurl2, answerurl1, conditiondata
from analyse import analysis
import sys

path = sys.path[0]
split_path = path + r'/ZhiHuAnswers/data/split_raw.txt'
non_split_path = path + r'/ZhiHuAnswers/data/raw.txt'


def is_Chinese(word):
    for ch in word:
        if '\u4e00' <= ch <= '\u9fff':
            return True
    return False


def get_keyword(data):
    for keyword, weight in extract_tags(data, withWeight=True, topK=1):
        return keyword


def question(url1, qid, url2):
    headers = {
        'Accept': "application/json, text/plain, */*",
        'User-Agent': "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
                      "Chrome/65.0.3325.181 Safari/537.36 "
    }
    url = url1 + qid + url2
    response = requests.get(url=url, headers=headers, timeout=300)
    text = response.text
    jtext = json.loads(text)
    datalist = []
    if 'data' not in jtext:
        return 0
    if len(jtext['data']) == 0:
        return 0
    for data in jtext['data']:
        Id = str(data.get('id'))
        answer_count = data.get('answer_count')
        data = {'id': Id, 'answer_count': answer_count}
        datalist.append(data)
        pass

    return datalist


def answer(url1, qid, url2, head):
    headers = {'Accept': "application/json, text/plain, */*", 'User-Agent': head}
    url = url1 + qid + url2
    response = requests.get(url=url, headers=headers, timeout=300)
    jtext = json.loads(s=response.text)
    contentdict = {}
    if len(jtext['data']) == 0:
        return 0
    for data in jtext['data']:
        aid = str(data['id'])
        rawcontext = data['content']
        if len(rawcontext) == 0:
            break
        context = pq(rawcontext).text()
        if context == '':
            break
        voteupcount = data.get('voteup_count')
        commentcount = data.get('comment_count')
        heat = voteupcount + commentcount * 2
        questiontext = data['question'].get('title')

        keyword = get_keyword(context)
        link = "https://www.zhihu.com/question/" + qid + "/answer/" + aid
        data = {'context': context, 'voteupcount': voteupcount, 'commentcount': commentcount,
                'heat': heat, 'keyword': keyword,
                'questiontext': questiontext, 'qid': qid, 'link': link}
        contentdict[aid] = data

    return contentdict


def daoService(anwserDict):
    a = analysis()
    j = 0
    for aid in anwserDict.keys():
        anwserDict[aid]['keyword'] = get_keyword(anwserDict[aid]['context'])
        anwserDict[aid]['feature'] = str(a.get(aid))
        opidao = OpiDao()
        try:
            result = opidao.createcontent(aid, anwserDict[aid])
            if result > 0:
                # if j % 50 == 0:
                j += 1
        except Exception as e:
            print(e)
            return 0
        finally:
            opidao.close()
    return 1


def txtService(answercontextList, answer_id_list):
    with open(split_path, 'w+', encoding='utf-8') as f:
        for (context, aid) in zip(answercontextList, answer_id_list):
            f.write(context + "\t\n")

    with open(non_split_path, 'w+', encoding='utf-8') as f:
        for (context, aid) in zip(answercontextList, answer_id_list):
            con = aid + '||' + context
            f.write(con + "\t\n")


def getqiddata(condition):
    conditions = parse.quote(condition)

    url = 'https://www.zhihu.com/api/v4/search_v3?gk_version=gz-gaokao&t=general&q=' + conditions + '&correction=1&offset=0&limit=20&filter_fields=&lc_idx=0&show_all_topics=0&search_source=Normal'
    header = {'Host': 'www.zhihu.com',
              'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:73.0) Gecko/20100101 Firefox/73.0',
              'accept': '*/*', 'Accept-Language': 'zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2',
              'x-api-version': '3.0.91', 'x-app-za': 'OS=Web', 'x-requested-with': 'fetch', 'x-zse-93': '101_3_3.0',
              'refer': 'https://www.zhihu.com/search?type=content&q=' + conditions,
              'Cookie': conditiondata[condition]['Cookie'],
              'x-zse-96': conditiondata[condition]['x-zse-96'], 'x-zst-81': conditiondata[condition]['x-zst-81']}
    response = requests.get(url=url, headers=header, timeout=300)
    jtext = json.loads(s=response.text)
    data = jtext['data']
    qidlist = []
    for aitem in data:
        if aitem['type'] == 'search_result':
            if aitem['object']['type'] == 'answer':
                id = aitem['object']['question']['id']
                anwswercount = getanswercount(id)
                qidlist.append({'id': id, 'answer_count': anwswercount})
    return qidlist


def getanswercount(id):
    url = 'https://www.zhihu.com/question/' + id
    header = {'Host': 'www.zhihu.com',
              'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:73.0) Gecko/20100101 Firefox/73.0',
              'accept': '*/*',
              'Accept-Language': 'zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2',
              'cookie': '__snaker__id=vikZ38f2Rk5Dl0OK; SESSIONID=V4t1VEkQmAkmIdRymr8SK9A2lUPLsTNrVtqPzD4mpMF; '
                        'JOID=VF8SAktFEZfiomjJU0JmSYXlY-BHdkTdk-9QuTM7KOSMwTyRMl2rnoalac9URIeZGdUaKVP7TbNWJ9876Y8c_KQ'
                        '=; '
                        'osd=UF0VC0tBE5DromzLVEtmTYfiauBDdEPUk-tSvjo7LOaLyDyVMFqinoKnbsZUQIWeENUeK1TyTbdUINY77Y0b9aQ=; '
                        '_zap=349469f4-dcbc-4e5a-8278-f6df4e69bebc; '
                        'd_c0="AOAR_z0MLhWPTj56AwT9SvgbRPuL_pIt4y8=|1656647469"; _9755xjdesxxd_=32; '
                        'YD00517437729195:WM_TID=CYw5iQEc94FEFUAUABeEVo9+/QhifOaC; '
                        '_xsrf=vp5iZdCn1tu26jKy2Fx5z5DkcUVzG4ib; __snaker__id=B0UukB4pQbDmeeyi; '
                        'YD00517437729195:WM_NI=0xjwTiwwqmtnrLc79KmXInn9EtRkcYN1JFaP1XZdCyVv3lLy60vzyOsjA'
                        '/ErVFAeyZYelG2dC3daXPsayGdY8d/dui7972cZfZtnHXBnzGYSPu+Pemrwb9CqtrkHY9ZeTnY=; '
                        'YD00517437729195:WM_NIKE=9ca17ae2e6ffcda170e2e6eeb4f44bb2ecbfb3d94692eb8aa7d85a839e9facd85da788f888c6728bb900bbe52af0fea7c3b92af4a8a698d763b5a9bf8ef766b294add8bc41af88878fed3387ac9e8ce2538eef86a7e45cf3889685c27396aafd8dcf73f6f0ba9bfc45b1b68fd0c87a969a8f86fc529bbba4a8b17081edac82db5cb1bfb7a8e753fcbce185f863f5e7f9d3e252a686bc8fcb5482b2b6a8dc5994abe5b1e867b2a9aedac27bb8aea5a9dc339abb9c8fdc37e2a3; l_cap_id="NDEwMDAyM2IwZjA5NDE5NmFmZjgxNWM5OTRiNDBiYmY=|1661326471|5515e9241468e8484fc6d9afc9f3366325cbc3d0"; r_cap_id="M2Y0MzBkYzgwZDlkNGFjODg2MTRlNzAyMGJlNzg4OTM=|1661326471|e59c0b50f1cfdff27ce280241bf461e6b8808c5e"; cap_id="M2UzNjhjNjBhNmEzNGI3NmE2ZjE2ZGYzMmEyMzExMTg=|1661326471|af846c16f8cfb9f6ed6c5acc721b255a43cc84b7"; captcha_session_v2=2|1:0|10:1661326478|18:captcha_session_v2|88:K2MrUjFIMDdFeE96RjhyN1NmSnEyUUpNUlFtMmpoSW9wblZoUFBWMEthQ2tpTGwrblJhN1NmU3pFZkcyWUc0TQ==|dd42d7c7e8e136b1b7aa28fab5ddcf9060b4d8fb26f48ab8aeffaeeeea48b97c; gdxidpyhxdE=hbhfnEQy8V9Et1OSiz8AazUE/t26KhgWdfyIUIKslpxw5hWoWqB2/YRzWcijm7WyNNfUTTTsn40LKuO9e6p7UIOa0hckuHQAzlLCGzBqRiHKJKvbPSj/xnI7MSIYhyEY1i5EG\HGhh8hwz3oM5Caipw2IKDjYa0CHc8LCPUXAJYzhPoI:1661327378883; z_c0=2|1:0|10:1661326483|4:z_c0|92:Mi4xQXQtTUNRQUFBQUFBNEJIX1BRd3VGUmNBQUFCZ0FsVk5reUx6WXdEZF9YZnc3MEs0VW05bTQwWjJEdS1KXzB4UlB3|abf8f4a790691bbbb907bcbcba6703b6e98a6439e872d8833ea7c5c65d852863; q_c1=9b001f8111724f4283add5e7e55b632b|1661326483000|1661326483000; Hm_lvt_98beee57fd2ef70ccdd5ca52b9740c49=1661317595,1661753273,1661836516,1661904935; tst=r; SESSIONID=2ULpK9pfx8shbRYa4tmovU2SATn5G6VUCp9Q0WxrtqQ; JOID=VF4VC0J_ZhMo-v9JI3EZykez_WgwTTVXWb7GP00IXmZImaIQRFHAFEX1-Ucq2CxXa8mfwRix4-xrSBYM6c74kzQ=; osd=VV0TBkt-ZRUl8_5KJXwQy0S18GExTjNaUL_FOUABX2VOlKsRR1fNHUT2_0oj2S9RZsCewh686u1oThsF6M3-nj0=; NOT_UNREGISTER_WAITING=1; Hm_lpvt_98beee57fd2ef70ccdd5ca52b9740c49=1661910157; KLBRSID=81978cf28cf03c58e07f705c156aa833|1661910339|1661904931'
              }
    response = requests.get(url=url, headers=header, timeout=300)
    text = response.content.decode()
    html = etree.HTML(text)
    xpath = '//*[@id="QuestionAnswers-answers"]/div/div/div/div[1]/h4/span/text()'
    return html.xpath(xpath)[0]


class Excute(object):
    def __init__(self, limit, condition):
        self.limit = limit
        self.condition = condition

    def door(self):
        opidao = OpiDao()
        if opidao.isempty():
            questionList = questionlist
        else:
            questionList = getqiddata(self.condition)
        for i in range(100):
            if i == len(questionList):
                break
            qlist = question(questionurl1, questionList[i].get('id'), questionurl2)
            if qlist != 0:
                for qitem in qlist:
                    if qitem not in questionList:
                        questionList.append(qitem)

        j = 1
        answerDict = {}
        answercontextList = []
        answer_id_list = []
        for questionitem in questionList:
            anwsercount = ''.join(questionitem.get('answer_count').split(","))
            end = int(anwsercount) - int(anwsercount) % 5
            for item in range(0, end, 5):
                if j == 410:
                    continue
                answerurl2 = '/answers?include=data%5B%2A%5D.is_normal%2Cadmin_closed_comment%2Creward_info' \
                             '%2Cis_collapsed' \
                             '%2Cannotation_action%2Cannotation_detail%2Ccollapse_reason%2Cis_sticky%2Ccollapsed_by' \
                             '%2Csuggest_edit' \
                             '%2Ccomment_count%2Ccan_comment%2Ccontent%2Ceditable_content%2Cattachment%2Cvoteup_count' \
                             '%2Creshipment_settings%2Ccomment_permission%2Ccreated_time%2Cupdated_time%2Creview_info' \
                             '%2Crelevant_info' \
                             '%2Cquestion%2Cexcerpt%2Cis_labeled%2Cpaid_info%2Cpaid_info_content%2Crelationship' \
                             '.is_authorized' \
                             '%2Cis_author%2Cvoting%2Cis_thanked%2Cis_nothelp%2Cis_recognized%3Bdata%5B%2A%5D.settings' \
                             '.table_of_content.enabled&limit=5&offset=' \
                             + str(item) + '&platform=desktop&sort_by=default'
                adict = answer(answerurl1, questionitem.get('id'), answerurl2, head=user_agent_list[item % 18])
                if adict != 0:
                    for aid in adict.keys():
                        if aid in answer_id_list:
                            continue
                        answerDict[aid] = adict[aid]
                        answercontextList.append(answerDict[aid]['context'])
                        answer_id_list.append(aid)
                        if j > self.limit - 1:
                            return answerDict, answercontextList, answer_id_list
                        j += 1


if __name__ == '__main__':
    execute = Excute(1000)
    answerDict, answercontextList, answer_id_list = execute.door()
    txtService(answercontextList, answer_id_list)
    daoService(answerDict)
