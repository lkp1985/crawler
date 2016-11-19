package com.lkp.common.net;

import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

public class TrustAnyTrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	@Override
	public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
			String authType) throws java.security.cert.CertificateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
			String authType) throws java.security.cert.CertificateException {

	}

	@Override
	public java.security.cert.X509Certificate[] getAcceptedIssuers() {

		return null;
	}
}
