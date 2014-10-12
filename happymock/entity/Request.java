package com.phei.netty.protocol.happymock.entity;

import io.netty.handler.codec.http.HttpRequest;

/**
 * Created by jicui on 10/12/14.
 */
public class Request {
    private HttpRequest httpRequest;
    private String uri;

    public String getUri() {
        return uri;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
