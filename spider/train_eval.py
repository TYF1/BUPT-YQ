# coding: UTF-8
import numpy as np
import torch


from tqdm import tqdm


def my_train(model, dataset, num_epoch, criterion, optimizer, device, batch_size, valid_data):
    for epoch in range(num_epoch):
        loss_list = []
        correct = 0
        item = 0
        model.train()
        for batch_x, batch_y in tqdm(dataset):
            batch_x, batch_y = batch_x.to(device), batch_y.to(device)
            output = model(batch_x)
            loss = criterion(output, batch_y)
            predict = torch.max(output.data, 1)[1]
            correct += (predict == batch_y).sum()
            optimizer.zero_grad()
            loss_list.append(loss.item())
            item += 1
            loss.backward()
            optimizer.step()
        v_loss, v_correct = validation(model, valid_data, device, criterion, batch_size)
        print(f'epoch:{epoch}, train_loss:{np.array(loss_list).mean(0)}, train_acc:{correct / (item * batch_size)}, '
              f'valid_loss:{v_loss}, valid_acc:{v_correct}')
        if v_correct > 0.52:
            torch.save(model.state_dict(), './my_model_dict/epoch_' + str(epoch) +
                       '_loss_' + str(v_loss) + '_acc_' + str(v_correct) + '_param.pt')


def validation(model, dataset, device, criterion, batch_size):
    model.eval()
    loss_list = []
    correct = 0
    item = 0
    with torch.no_grad():
        for batch_x, batch_y in tqdm(dataset):
            batch_x, batch_y = batch_x.to(device), batch_y.to(device)
            output = model(batch_x)
            loss = criterion(output, batch_y)
            predict = torch.max(output.data, 1)[1]
            correct += (predict == batch_y).sum()
            loss_list.append(loss.item())
            item += 1
    return np.array(loss_list).mean(0), correct / (item * batch_size)
