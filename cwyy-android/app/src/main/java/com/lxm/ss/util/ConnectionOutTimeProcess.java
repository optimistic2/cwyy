package com.lxm.ss.util;

import android.os.Handler;

public class ConnectionOutTimeProcess implements Runnable {
	public static final int OUT_TIME = 0;

	public boolean running = false;
	private long startTime = 0L;
	private Thread thread = null;
	private Handler hdl = null;

	public ConnectionOutTimeProcess(Handler hdl) {
		this.hdl = hdl;
	}

	public void run() {
		while (true) {
			if (!this.running)
				return;
			if (System.currentTimeMillis() - this.startTime > 20 * 1000L) {
				if(hdl != null){
					hdl.sendEmptyMessage(OUT_TIME);
				}
			}
			try {
				Thread.sleep(10L);
			} catch (Exception localException) {
			}
		}
	}

	public void start() {
		try {
			this.thread = new Thread(this);
			this.running = true;
			this.startTime = System.currentTimeMillis();
			this.thread.start();
		} finally {
		}
	}

	public void stop() {
		try {
			this.running = false;
			this.thread = null;
			this.startTime = 0L;
		} finally {
		}
	}
}