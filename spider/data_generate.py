

raw_path='split_raw.txt'
train_file='./ZhihuData/data/train.txt'
valid_file='./ZhihuData/data/dev.txt'
test_file='./ZhihuData/data/test.txt'
raw_file='./ZhihuData/data/raw_data.txt'
with open(raw_path,encoding='utf-8') as f:
    raw=[]
    for line in f:
        raw.append(line)

train_set=raw[:3501].copy()
valid_set=raw[3501:4501].copy()
test_set=raw[4501:5001].copy()
raw_data=raw[5001:].copy()

with open(train_file,'w',encoding='utf-8') as f:
    for i in train_set:
        f.write(i)
with open(valid_file, 'w', encoding='utf-8') as f:
    for i in valid_set:
        f.write(i)
with open(test_file, 'w', encoding='utf-8') as f:
    for i in test_set:
        f.write(i)
with open(raw_file, 'w', encoding='utf-8') as f:
    for i in raw_data:
        f.write(i)
