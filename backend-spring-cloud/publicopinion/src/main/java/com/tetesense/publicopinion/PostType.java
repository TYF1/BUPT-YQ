package com.tetesense.publicopinion;

public enum PostType {

    ALL (0),
    RUMORS (1),
    LOCKDOWNS (2),
    REOPENING (3),
    ;

    private final int type;

    public int getType() {
        return type;
    }

    PostType(int type) {
        this.type = type;
    }

}
