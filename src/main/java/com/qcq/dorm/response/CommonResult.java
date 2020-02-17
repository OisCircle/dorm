package com.qcq.dorm.response;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

/**
 * <p>
 * 公共回复类
 * </p>
 *
 * @author O
 * @version 1.0
 * @since 2018/11-20
 */
@Data
@Accessors(chain = true)
@Slf4j
public class CommonResult {
	private final static int SUCCESS_CODE = 200;
	private final static int FAILURE_CODE = 250;
	private final static String SUCCESS_STRING = "请求成功";
	private final static String FAILURE_STRING = "请求失败";

	private int code;
	private boolean success;
	private String message;
	private Object data;

	public static CommonResult success(Object data) {
		return success().setData(data);
	}

	public static CommonResult success() {
		return new CommonResult()
				.setCode(SUCCESS_CODE)
				.setSuccess(true)
				.setMessage(SUCCESS_STRING);
	}

	public static CommonResult failure(String message) {
		return failure().setMessage(message);
	}

	public static CommonResult failure() {
		return new CommonResult()
				.setCode(FAILURE_CODE)
				.setSuccess(false)
				.setMessage(FAILURE_STRING);
	}

	/**
	 * 根据boolean自动选择返回成功/失败实例
	 */
	public static CommonResult expect(boolean success) {
		return success ? success() : failure();
	}

	/**
	 * 配合@expect（）方法使用
	 */
	public CommonResult setSuccessMessage(String message) {
		return isSuccess() ? setMessage(message) : this;
	}

	/**
	 * 配合@expect（）方法使用
	 */
	public CommonResult setFailureMessage(String message) {
		return !isSuccess() ? setMessage(message) : this;
	}

	/**
	 * 配合@expect（）方法使用
	 */
	public CommonResult setSuccessData(Object data) {
		return isSuccess() ? setData(data) : this;
	}

	/**
	 * 根据HttpServletResponse输出到客户端
	 */
	public void io(HttpServletResponse response) {
		try (ServletOutputStream stream = response.getOutputStream()) {
			response.setContentType("application/json;charset=utf-8");
			String result = JSONObject.toJSONString(this);
			stream.write(result.getBytes(Charset.forName("utf-8")));
		} catch (Exception e) {
			log.error("an error occurred when sending servlet response ...");
			e.printStackTrace();
		}
	}
}


