package com.httpclient.utils.beans;

/**
 * @author mdc
 * @date 2016年9月3日
 */
public class TongjiData {
	private String name;
	private String uv;
	private String pv;
	private String v;

	/**
	 * @return 获取{@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 设置name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 获取{@link #uv}
	 */
	public String getUv() {
		return uv;
	}

	/**
	 * @param uv 设置uv
	 */
	public void setUv(String uv) {
		this.uv = uv;
	}

	/**
	 * @return 获取{@link #pv}
	 */
	public String getPv() {
		return pv;
	}

	/**
	 * @param pv 设置pv
	 */
	public void setPv(String pv) {
		this.pv = pv;
	}

	/**
	 * @return 获取{@link #v}
	 */
	public String getV() {
		return v;
	}

	/**
	 * @param v 设置v
	 */
	public void setV(String v) {
		this.v = v;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TongjiData [name=");
		builder.append(name);
		builder.append(", uv=");
		builder.append(uv);
		builder.append(", pv=");
		builder.append(pv);
		builder.append(", v=");
		builder.append(v);
		builder.append("]");
		return builder.toString();
	}

}

