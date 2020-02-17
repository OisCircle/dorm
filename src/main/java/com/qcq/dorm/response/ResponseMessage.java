package com.qcq.dorm.response;

/**
 * @author O
 * @since 2020/2/15
 */
public class ResponseMessage {
    private int code;
    private String message;
    private Object data;

    public ResponseMessage(ResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public ResponseMessage(ResponseCode code, Object data) {
        this(code);
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
