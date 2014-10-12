package com.phei.netty.protocol.happymock.entity;

/**
 * Created by jicui on 10/12/14.
 */
public class Response {
    private String content;
    private boolean isKeepAlive;

    public Response(String content) {
        this.content = content;
    }

    public Response(String content, boolean isKeepAlive) {
        this.content = content;
        this.isKeepAlive = isKeepAlive;
    }

    public String getContent() {
        return content;
    }

    public boolean isKeepAlive() {
        return isKeepAlive;
    }

    public void setKeepAlive(boolean isKeepAlive) {
        this.isKeepAlive = isKeepAlive;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
