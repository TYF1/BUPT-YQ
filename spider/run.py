import sys

from Excute import Excute, txtService, daoService

limit = int(sys.argv[1])
search_content = sys.argv[2]
execute = Excute(limit,search_content)

answerDict, answercontextList, answer_id_list = execute.door()

txtService(answercontextList, answer_id_list)

daoService(answerDict)

