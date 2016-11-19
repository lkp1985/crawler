package com.lkp.project.court;

public class SecThread implements Runnable{

	@Override
	public void run() {
		while(true){
			TaskCollector.secQueue.push("");
		}
	}
	
}
