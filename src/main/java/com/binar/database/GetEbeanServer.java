package com.binar.database;

import com.avaje.ebean.EbeanServer;

public class GetEbeanServer {

	private  EbeanServer server;
	public EbeanServer getServer(){
		if(server==null){
			EbeanConfig config=new EbeanConfig();
			server= config.setConfig();
		} 
		return server;
	}

}
