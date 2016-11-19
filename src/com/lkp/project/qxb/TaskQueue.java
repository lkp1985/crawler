/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.qxb;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <p>class function description.<p>
 *
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class TaskQueue extends ConcurrentLinkedQueue<String> {

    private static final long serialVersionUID = 1L;

	private int maxSize = 1000;

	public boolean isFull() {
		return this.size() >= maxSize ? true : false;
	}

	public boolean push(String e){
		if (this.size() >= this.maxSize){
			return false;
		}
		return offer(e);	
	}
	
	public String pull(){
		return poll();
	}

	/**
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param maxSize
	 *            the maxSize to set
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

}
