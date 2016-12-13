package org.cwyy.log.utils;

public class ThreadCallerIdGenerator {
	public static String genCallerThreadId() {
		return Thread.currentThread().getId() + "_" + System.currentTimeMillis();
	}
}