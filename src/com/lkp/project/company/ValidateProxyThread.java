package com.lkp.project.company;

import com.lkp.proxyutils.ProxyUtil;

public class ValidateProxyThread implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ProxyUtil.initProxy();
	}

}
