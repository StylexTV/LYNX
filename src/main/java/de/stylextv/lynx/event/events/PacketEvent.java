package de.stylextv.lynx.event.events;

import de.stylextv.lynx.event.Event;
import de.stylextv.lynx.event.EventListener;
import net.minecraft.network.Packet;

public class PacketEvent extends Event {
	
	private Type type;
	
	private Packet<?> packet;
	
	public PacketEvent(Type type, Packet<?> packet) {
		this.type = type;
		this.packet = packet;
	}
	
	@Override
	public void callListener(EventListener l) {
		if(type == Type.SENT) {
			
			l.onPacketSent(this);
			
		} else {
			
			l.onPacketReceived(this);
		}
	}
	
	public Type getType() {
		return type;
	}
	
	public Packet<?> getPacket() {
		return packet;
	}
	
	public enum Type {
		
		SENT, RECEIVED;
		
	}
	
}
