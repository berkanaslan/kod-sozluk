package com.berkanaslan.eksisozlukclone.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper {

    private final String status;
    private final String detail;
    private final Object data;

    // Success
    public ResponseWrapper(String status, String detail) {
        this.status = status;
        this.detail = detail;
        this.data = null;
    }

    // Error
    public ResponseWrapper(String status, Object data) {
        this.status = status;
        this.detail = null;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public Object getData() {
        return data;
    }
}

