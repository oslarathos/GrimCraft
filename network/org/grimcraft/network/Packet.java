package org.grimcraft.network;

import java.util.ArrayList;

public class Packet {
	public static Packet reconstruct( String line ) throws Exception {
		try {
			String[] pieces = line.split( "\u001E" );
			
			Packet packet = new Packet( Protocol.lookup( Integer.parseInt( pieces[ 0 ] ) ) );
			
			for ( int index = 1; index < pieces.length; index++ ) {
				packet.appendPiece( pieces[ 1 ] );
			}
			
			return packet;
		} catch ( Exception e ) {
			System.out.println( "Failed to reconstruct packet from: " + line );
			
			return null;
		}
	}
	
	private Protocol protocol;
	private ArrayList< String > pieces = new ArrayList< String >();
	
	public Packet( Protocol protocol ) {
		this.protocol = protocol;
	}
	
	public Protocol getProtocol() {
		return protocol;
	}
	
	public void appendPiece( String piece ) {
		pieces.add( piece );
	}
	
	public ArrayList< String > getPieces() {
		return pieces;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( Integer.toString( protocol.getID() ) );
		
		for ( String piece : pieces ) {
			buffer.append( "\u001E" + piece.replaceAll( "\u001E", "|001E" ) );
		}
		
		return buffer.toString();
	}
}
