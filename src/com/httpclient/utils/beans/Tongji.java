package com.httpclient.utils.beans;

/**
 * @author mdc
 * @date 2016年9月3日
 */
public class Tongji {

	private String state;
	private String message;
	private TongjiData data;
	/**
	 * @return 获取{@link #state}
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state 设置state
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return 获取{@link #message}
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message 设置message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return 获取{@link #data}
	 */
	public TongjiData getData() {
		return data;
	}
	/**
	 * @param data 设置data
	 */
	public void setData(TongjiData data) {
		this.data = data;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tongji [state=");
		builder.append(state);
		builder.append(", message=");
		builder.append(message);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}
	
	
}
