package org.grimcraft.network;

import java.util.HashMap;

public enum Protocol {
	Disconnect( 0 ),
	Login( 1 ),
	BadLogin( 2 ),
	Message( 3 ),
	
	Enable( 10 )
	
	
	;
	
	private static HashMap< Integer, Protocol > lookupMap = new HashMap<Integer, Protocol>();
	
	static {
		for ( Protocol proto : values() ) {
			lookupMap.put( proto.getID(), proto );
		}
	}
	
	public static Protocol lookup( int id ) {
		return lookupMap.get( id );
	}
	
	private final int id;
	
	private Protocol( int id ) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
