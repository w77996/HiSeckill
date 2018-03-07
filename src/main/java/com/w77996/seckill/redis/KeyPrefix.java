package com.w77996.seckill.redis;

public interface KeyPrefix {

    public int expireSeconds();
    public String getPrefix();
}
