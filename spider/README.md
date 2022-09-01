# 文本分类（预处理用到Bert-Chinese-Text-Classification-Pytorch的词典）

中文文本分类，训练的数据集是自己手动标注的知乎答案，只有3500条，而且数据不是很好（

## 介绍
简单的LSTM+Attention机制

训练环境：
3090Ti  大约15个epoch差不多可用

## 环境
python 3.7  
pytorch 1.1  
tqdm  
numpy
sklearn  


## 中文数据集
自己爬的知乎问题，主要是和疫情相关的
分类：0消极，1积极，2中立

数据集划分：

数据集|数据量
--|--
训练集|3500
验证集|500


### 数据格式
 -   文本+\t+类型




## 使用说明
下载好预训练模型就可以跑了。
``python my_run.py``
``python analyse.py``
但是如果是我们这个小组项目的话不用单独管他

