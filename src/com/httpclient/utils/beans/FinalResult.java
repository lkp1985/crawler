package com.httpclient.utils.beans;

import java.util.Arrays;

/**
 * @author mdc
 * @date 2016��9��3��
 */
public class FinalResult {
	private String companyCount;
	private String humanCount;
	private String message;
	private String state;
	private Integer total;
	private Integer totalPage;
	
	private Detail[] data;

	/**
	 * @return ��ȡ{@link #companyCount}
	 */
	public String getCompanyCount() {
		return companyCount;
	}

	/**
	 * @param companyCount ����companyCount
	 */
	public void setCompanyCount(String companyCount) {
		this.companyCount = companyCount;
	}

	/**
	 * @return ��ȡ{@link #humanCount}
	 */
	public String getHumanCount() {
		return humanCount;
	}

	/**
	 * @param humanCount ����humanCount
	 */
	public void setHumanCount(String humanCount) {
		this.humanCount = humanCount;
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
	 * @return ��ȡ{@link #total}
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total ����total
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return ��ȡ{@link #totalPage}
	 */
	public Integer getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage ����totalPage
	 */
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * @return ��ȡ{@link #data}
	 */
	public Detail[] getData() {
		return data;
	}

	/**
	 * @param data ����data
	 */
	public void setData(Detail[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FinalResult [companyCount=");
		builder.append(companyCount);
		builder.append(", humanCount=");
		builder.append(humanCount);
		builder.append(", message=");
		builder.append(message);
		builder.append(", state=");
		builder.append(state);
		builder.append(", total=");
		builder.append(total);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append(Arrays.toString(data));
		builder.append("]");
		return builder.toString();
	}
}
