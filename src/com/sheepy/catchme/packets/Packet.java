package com.sheepy.catchme.packets;

import com.sheepy.catchme.GameClient;
import com.sheepy.catchme.GameServer;

public abstract class Packet {

	public static enum PacketTypes {
		INVAILD(-1),
		LOGIN(00),
		DISCONNECT(01);

		private int packetId;

		private PacketTypes(int packetId) {
			this.packetId = packetId;
		}

		public int getId() {
			return packetId;
		}
	}

	public byte packetId;

	public Packet(int packetId) {
		this.packetId = (byte) packetId;
	}

	public abstract void writeData(GameClient client);
	public abstract void writeData(GameServer server);

	public abstract byte[] getData();

	public String readData(byte[] data) {
		String message = new String(data);
		return message.substring(2);
	}

	public static PacketTypes lookupPacket(int id) {
		for (PacketTypes p : PacketTypes.values()) {
			if (p.getId() == id) {
				return p;
			}
		}
		return PacketTypes.INVAILD;
	}

	public static PacketTypes lookupPacket(String message) {
		try {
			return lookupPacket(Integer.parseInt(message.substring(0, 2)));
		} catch (NumberFormatException e) {
			return PacketTypes.INVAILD;
		}
	}
}