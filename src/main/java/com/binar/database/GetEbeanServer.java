package com.binar.database;

import com.avaje.ebean.EbeanServer;

public class GetEbeanServer {

	private static EbeanServer server;
	public static EbeanServer getServer(){
		if(server==null){
			EbeanConfig config=new EbeanConfig();
			server= config.setConfig();
		} 
		return server;
	}
}
