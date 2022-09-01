package com.tetesense.publicopinion.weibo.stat;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeiboStatResponseBody {
    private int negative;
    private int positive;
    private int neutral;
    private int total;
}
