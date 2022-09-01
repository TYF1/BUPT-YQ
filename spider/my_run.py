
import time
import torch
from torch import nn
import numpy as np
from importlib import import_module
from models.my_rnn_attention import RNN
from train_eval import my_train
from utils import build_dataset
import torch.optim as optim
import torch.utils.data as Data

device=torch.device("cuda" if torch.cuda.is_available() else "cpu")

if __name__=='__main__':
    batch_size=16
    vocab_size, embedding_dim, hidden_size, num_classes, num_layers=18000,256,128,3,1
    dataset = 'ZhiHuAnswers'
    start_time = time.time()
    x = import_module('models.' + 'bert')
    config = x.Config(dataset)
    print("Loading data...")

    train_data, dev_data = build_dataset(config,mode='train')
    train_data_X,train_data_Y=train_data
    valid_data_X,valid_data_Y=dev_data
    train_data=Data.TensorDataset(torch.tensor(train_data_X),torch.tensor(train_data_Y))
    valid_data=Data.TensorDataset(torch.tensor(valid_data_X),torch.tensor(valid_data_Y))

    train_loader=Data.DataLoader(
        dataset=train_data,
        batch_size=batch_size,
        shuffle=True,
        num_workers=2
    )
    valid_loader=Data.DataLoader(
        dataset=valid_data,
        batch_size=batch_size,
        shuffle=True,
        num_workers=2,
    )
    model=RNN(vocab_size, embedding_dim, hidden_size, num_classes, num_layers)
    model=model.to(device)
    criterion=nn.CrossEntropyLoss()
    optimizer=optim.Adam(model.parameters(),lr=2e-4)
    my_train(model,dataset=train_loader,num_epoch=25,criterion=criterion,optimizer=optimizer,
             device=device,batch_size=batch_size,valid_data=valid_loader)
