package com.lkp.court;

public class FirThread implements Runnable{

	@Override
	public void run() {
		while(true){
			TaskCollector.firQueue.push("");
		}
	}
	
}
