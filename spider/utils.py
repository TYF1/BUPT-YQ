# coding: UTF-8
import sys

import torch
from tqdm import tqdm
import time
from datetime import timedelta

PAD, CLS = '[PAD]', '[CLS]'  # padding符号, bert中综合信息符号
abs_path = sys.path[0]

raw_path = abs_path + r'/ZhiHuAnswers/data/split_raw.txt'


def build_dataset(config, mode):
    def load_dataset(path, pad_size=32):
        contents = []
        with open(path, 'r', encoding='UTF-8') as f:
            for line in tqdm(f):
                lin = line.strip()
                if not lin:
                    continue
                content, label = lin.split('\t')
                token = config.tokenizer.tokenize(content)
                token = [CLS] + token
                seq_len = len(token)
                mask = []
                token_ids = config.tokenizer.convert_tokens_to_ids(token)

                if pad_size:
                    if len(token) < pad_size:
                        mask = [1] * len(token_ids) + [0] * (pad_size - len(token))
                        token_ids += ([0] * (pad_size - len(token)))
                    else:
                        mask = [1] * pad_size
                        token_ids = token_ids[:pad_size]
                        seq_len = pad_size
                contents.append((token_ids, int(label), seq_len, mask))
        return contents

    def my_load(path, pad_size):
        x = []
        y = []
        with open(path, 'r', encoding='UTF-8') as f:
            for line in tqdm(f):
                lin = line.strip()
                if not lin:
                    continue
                content, label = lin.split('\t')
                token = config.tokenizer.tokenize(content)
                token = [CLS] + token
                token_ids = config.tokenizer.convert_tokens_to_ids(token)

                if pad_size:
                    if len(token) < pad_size:
                        token_ids += ([0] * (pad_size - len(token)))
                    else:
                        token_ids = token_ids[:pad_size]
                x.append(token_ids)
                y.append(int(label))

        return (x, y)

    def load_pure_text(path, pad_size):
        x = []
        text = []
        with open(path, 'r', encoding='UTF-8') as f:
            for line in tqdm(f):
                lin = line.strip()
                if lin in ['\n', '\r\n'] or lin == "":
                    continue
                content = line.strip('\t')
                text.append(content)
                token = config.tokenizer.tokenize(content)
                token = [CLS] + token
                token_ids = config.tokenizer.convert_tokens_to_ids(token)
                if pad_size:
                    if len(token) < pad_size:
                        token_ids += ([0] * (pad_size - len(token)))
                    else:
                        token_ids = token_ids[:pad_size]
                x.append(token_ids)

        return (x, text)

    # train = load_dataset(config.train_path, config.pad_size)
    # dev = load_dataset(config.dev_path, config.pad_size)
    if mode == 'train':
        train = my_load(config.train_path, config.pad_size)
        dev = my_load(config.dev_path, config.pad_size)
        return train, dev
    elif mode == 'utilize':
        text = load_pure_text(raw_path, config.pad_size)
        return text


def get_time_dif(start_time):
    """获取已使用时间"""
    end_time = time.time()
    time_dif = end_time - start_time
    return timedelta(seconds=int(round(time_dif)))
