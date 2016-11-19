package com.httpclient.utils.beans;

import java.util.Arrays;

/**
 * @author mdc
 * @date 2016年9月3日
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
	 * @return 获取{@link #companyCount}
	 */
	public String getCompanyCount() {
		return companyCount;
	}

	/**
	 * @param companyCount 设置companyCount
	 */
	public void setCompanyCount(String companyCount) {
		this.companyCount = companyCount;
	}

	/**
	 * @return 获取{@link #humanCount}
	 */
	public String getHumanCount() {
		return humanCount;
	}

	/**
	 * @param humanCount 设置humanCount
	 */
	public void setHumanCount(String humanCount) {
		this.humanCount = humanCount;
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
	 * @return 获取{@link #total}
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total 设置total
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return 获取{@link #totalPage}
	 */
	public Integer getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage 设置totalPage
	 */
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * @return 获取{@link #data}
	 */
	public Detail[] getData() {
		return data;
	}

	/**
	 * @param data 设置data
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
