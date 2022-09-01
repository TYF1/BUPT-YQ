import torch
from torch import nn
import torch.nn.functional as F


class RNN(nn.Module):
    def __init__(self, vocab_size, embedding_dim, hidden_size, num_classes, num_layers):
        super(RNN, self).__init__()
        self.vocab_size = vocab_size
        self.embedding_dim = embedding_dim
        self.hidden_size = hidden_size
        self.num_classes = num_classes
        self.num_layers = num_layers

        self.embedding = nn.Embedding(self.vocab_size, self.embedding_dim, padding_idx=0)
        self.lstm = nn.LSTM(input_size=self.embedding_dim, hidden_size=self.hidden_size, num_layers=num_layers,
                            batch_first=True, bidirectional=False)
        self.relu = nn.ReLU()
        self.linear = nn.Linear(hidden_size, num_classes)
        self.dropout = nn.Dropout(0.3)

        self.norm = nn.BatchNorm1d(num_classes)

    def attention(self, rnn_output, final_state):
        batch_size = len(rnn_output)
        # hidden:[batch_size,n_hidden*num_directions,n_layer
        hidden = final_state.view(batch_size, -1, self.num_layers)
        # attn_weights: [batch_size,n_step]
        # (16,128,64)  (16,64,1)  ->  (16,128,1)
        attn_weights = torch.bmm(rnn_output, hidden).squeeze(2)
        soft_attn_weights = F.softmax(attn_weights, 1)
        context = torch.bmm(rnn_output.transpose(1, 2), soft_attn_weights.unsqueeze(2)).squeeze(2)
        return context, soft_attn_weights

    def forward(self, x):
        x = self.embedding(x)
        out, (final_hidden_state, final_state) = self.lstm(x)
        output, attention = self.attention(out, final_hidden_state)
        out = self.linear(output)
        out = self.dropout(out)
        out = self.norm(out)
        return out
