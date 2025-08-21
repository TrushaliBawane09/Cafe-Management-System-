package com.auth_service.payload;

public class ApiResponse {

    private Object message;
    private boolean success;
    private int statusCode;

    public ApiResponse(Object message, boolean success, int statusCode) {
        this.message = message;
        this.success = success;
        this.statusCode = statusCode;
    }

    public Object getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
