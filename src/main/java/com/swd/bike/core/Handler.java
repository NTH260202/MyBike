package com.swd.bike.core;

public interface Handler<T extends RequestData, I extends ResponseData> {
    I handle(T var1);
}
