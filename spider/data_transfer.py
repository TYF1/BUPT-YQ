import sys

import numpy as np

abs_path=sys.path[0]

output_path = abs_path+r'/ZhiHuAnswers/data/output.txt'
raw_path = abs_path+r'/ZhiHuAnswers/data/raw.txt'



def answer_match():
    sentences_with_id = []
    aid_list = []
    index_list = []
    labels = []
    with open(raw_path, 'r', encoding='UTF-8') as f:
        for line in f:
            lin = line.strip()
            if lin in ['\n', '\r\n'] or lin == "":
                continue
            temp = lin.split('||')
            if len(temp) == 2:
                if temp[1] in ['\n', '\r\n', '\t', '\t\n'] or temp[1] == "":
                    continue
            sentences_with_id.append(lin)
    with open(output_path, 'r', encoding='UTF-8') as f:
        for line in f:
            lin = line.strip()
            label = lin.split('\t')[1]
            labels.append(int(label))
    t_idx = []
    aid = 0
    length= len(labels)
    sentences_with_id=sentences_with_id[:length]
    for i, sentence in enumerate(sentences_with_id):
        content = sentence.split('||')
        if len(content) == 2:
            if i != 0:
                aid_list.append(aid)
                index_list.append(t_idx)
                t_idx = []
            aid = content[0]
        t_idx.append(i)
    aid_list.append(aid)
    index_list.append(t_idx)
    labels = np.array(labels)
    label_slices = []
    for idx in index_list:
        label_slices.append(labels[idx])
    label_slices = np.array(label_slices, dtype=object)
    out = {}
    for id, label in zip(aid_list, label_slices):
        l = np.argmax(np.bincount(label))
        out[id]=l

    return out


if __name__ == '__main__':
    b=answer_match()
    print('A')

