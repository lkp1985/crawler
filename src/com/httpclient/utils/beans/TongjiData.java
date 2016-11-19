package com.httpclient.utils.beans;

/**
 * @author mdc
 * @date 2016��9��3��
 */
public class TongjiData {
	private String name;
	private String uv;
	private String pv;
	private String v;

	/**
	 * @return ��ȡ{@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name ����name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return ��ȡ{@link #uv}
	 */
	public String getUv() {
		return uv;
	}

	/**
	 * @param uv ����uv
	 */
	public void setUv(String uv) {
		this.uv = uv;
	}

	/**
	 * @return ��ȡ{@link #pv}
	 */
	public String getPv() {
		return pv;
	}

	/**
	 * @param pv ����pv
	 */
	public void setPv(String pv) {
		this.pv = pv;
	}

	/**
	 * @return ��ȡ{@link #v}
	 */
	public String getV() {
		return v;
	}

	/**
	 * @param v ����v
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

