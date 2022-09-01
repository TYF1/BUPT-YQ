# coding: UTF-8
import os
import sys

import torch
import torch.nn as nn
# from pytorch_pretrained_bert import BertModel, BertTokenizer
from pytorch_pretrained import BertTokenizer


class Config(object):
    """配置参数"""

    def __init__(self, dataset):
        self.model_name = 'bert'
        self.train_path = dataset + '/data/train.txt'  # 训练集
        self.dev_path = dataset + '/data/dev.txt'  # 验证集
        self.test_path = dataset + '/data/test.txt'  # 测试集
        self.abs_path = os.path.join(os.path.abspath(os.path.dirname(__file__)),os.path.pardir)
        self.class_list = [x.strip() for x in open(self.abs_path + r'/' +
                                                   dataset + '/data/class.txt').readlines()]  # 类别名单

        self.save_path = dataset + '/saved_dict/' + self.model_name + '.ckpt'  # 模型训练结果
        self.device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')  # 设备

        self.require_improvement = 1000  # 若超过1000batch效果还没提升，则提前结束训练
        self.num_classes = len(self.class_list)  # 类别数
        self.num_epochs = 6  # epoch数
        self.batch_size = 32  # mini-batch大小
        self.pad_size = 128  # 每句话处理成的长度(短填长切)
        self.learning_rate = 5e-5  # 学习率
        self.bert_path = 'C:/Users/10569/Desktop/teledemo/teledemo/spider/bert_pretrain'
        self.bert_path = self.abs_path + r'/bert_pretrain'
        self.tokenizer = BertTokenizer.from_pretrained(self.bert_path)
        self.hidden_size = 64
        self.hidden_dropout_prob = 0.3
