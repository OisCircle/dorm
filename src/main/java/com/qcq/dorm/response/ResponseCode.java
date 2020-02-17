package com.qcq.dorm.response;

/**
 * @Author O
 * @Description
 * @Date 2018/4/24 17:19
 * @Version 1.0
 */
public enum  ResponseCode {
	SUCCESS(200, "请求成功"),
	FAILUREA(250,"请求失败");

	private int code;
	private String message;

	ResponseCode(int code, String message) {
		this.code=code;
		this.message = message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
