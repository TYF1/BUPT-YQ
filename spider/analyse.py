import sys
import time
import torch

from importlib import import_module

import torch.utils.data as Data

import data_transfer
from models.my_rnn_attention import RNN
from utils import build_dataset

abs_path = sys.path[0]

model_path = abs_path + r'/my_model_dict/usethis.pt'
output_path = abs_path + r'/ZhiHuAnswers/data/output.txt'
device = torch.device('cpu')


def tag(model, dataset):
    labels = []
    for batch_x in dataset:
        batch_x = batch_x[0].to(device)
        output = model(batch_x)
        predict = torch.max(output.data, 1)[1]
        labels += predict.tolist()
    return labels


def analysis():
    batch_size = 2
    vocab_size, embedding_dim, hidden_size, num_classes, num_layers = 18000, 256, 128, 3, 1
    dataset = 'ZhiHuAnswers'
    start_time = time.time()
    x = import_module('models.' + 'bert')
    config = x.Config(dataset)

    untagged_data, texts = build_dataset(config, mode='utilize')
    train_data = Data.TensorDataset(torch.tensor(untagged_data))
    train_loader = Data.DataLoader(
        dataset=train_data,
        batch_size=batch_size,
        drop_last=True
    )
    model = RNN(vocab_size, embedding_dim, hidden_size, num_classes, num_layers)
    model.load_state_dict(torch.load(model_path, map_location=torch.device('cpu')))
    labels = tag(model, train_loader)
    with open(output_path, 'w+', encoding='UTF-8') as f:
        for text, label in zip(texts, labels):
            text = text.strip('\n').strip('\t')
            sentence = text + '\t' + str(label) + '\n'
            f.write(sentence)
    a = data_transfer.answer_match()
    return a
