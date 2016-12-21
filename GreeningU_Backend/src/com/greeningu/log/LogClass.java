package com.greeningu.log;

import org.apache.log4j.Logger;

public class LogClass {
	
	private static org.apache.log4j.Logger log = Logger.getLogger(LogClass.class.getName());
	
	public static void msg(String msg) {

		log.debug(msg);
	}
		
	public static void exMsg(String msg, Exception ex) {

		log.debug(msg, ex);
	}
}

