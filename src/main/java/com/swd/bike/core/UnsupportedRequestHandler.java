package com.swd.bike.core;

public class UnsupportedRequestHandler extends RequestHandler<BaseRequestData, BaseResponseData> {
    private static final UnsupportedRequestHandler INSTANCE = new UnsupportedRequestHandler();

    public UnsupportedRequestHandler() {
    }

    public static UnsupportedRequestHandler getInstance() {
        return INSTANCE;
    }

    public BaseResponseData handle(BaseRequestData requestData) {
        return null;
    }
}
