package com.tetesense.publicopinion.weibo.post;

public enum PostType {

    ALL (0),
    RUMORS (1),
    LOCKDOWNS (2),
    REOPENING (3),
    ;

    public static String keyword[] = {
            null,
            "谣言",
            "封",
            "解"
    };

    private final int type;

    public int getType() {
        return type;
    }

    PostType(int type) {
        this.type = type;
    }

}
