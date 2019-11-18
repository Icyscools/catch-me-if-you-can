package com.sheepy.catchme.util;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Database {

	private String ipaddr; 
	private int port;
	private MongoClient mongoClient;

	public Database() throws UnknownHostException {
		this("127.0.0.1", 27017);
	}

	public Database(String ipaddr) throws UnknownHostException {
		this(ipaddr, 27017);
	}

	public Database(String ipaddr, int port) throws UnknownHostException {
		this.ipaddr = ipaddr;
		this.port = port;
		this.mongoClient = new MongoClient(ipaddr, port);
		System.out.println("Connection Established");
	}
	
	public String getConnectionIp() {
		return this.ipaddr;
	}
	
	public int getConnectionPort() {
		return this.port;
	}

	public DB getDatabase(String dbName) {
		return this.mongoClient.getDB(dbName);
	}

	public DBCollection getCollection(DB db, String collectionName) {
		return db.getCollection(collectionName);
	}

}
