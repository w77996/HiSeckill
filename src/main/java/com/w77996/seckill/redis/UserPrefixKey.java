package com.w77996.seckill.redis;

public class UserPrefixKey extends BasePrefix {
    private static final int TOKEN_EXPREFI = 3600*24*2;
    public UserPrefixKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserPrefixKey token = new UserPrefixKey(TOKEN_EXPREFI,"tk");
}
