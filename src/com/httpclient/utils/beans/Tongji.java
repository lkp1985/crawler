package com.httpclient.utils.beans;

/**
 * @author mdc
 * @date 2016��9��3��
 */
public class Tongji {

	private String state;
	private String message;
	private TongjiData data;
	/**
	 * @return ��ȡ{@link #state}
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state ����state
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return ��ȡ{@link #message}
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message ����message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return ��ȡ{@link #data}
	 */
	public TongjiData getData() {
		return data;
	}
	/**
	 * @param data ����data
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
