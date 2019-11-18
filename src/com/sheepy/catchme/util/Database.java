package com.sheepy.catchme.util;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;

public class Database {
	
	private String ipaddr;
	private int port;
	private MongoClient db;
	
	public Database(String ipaddr, int port) throws UnknownHostException {
		this.ipaddr = ipaddr;
		this.port = port;
		this.db = new MongoClient(ipaddr, port);
	}

}
