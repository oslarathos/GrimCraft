package org.grimcraft.network.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.grimcraft.network.Packet;
import org.grimcraft.network.Protocol;

public class Connection extends Thread {
	private Socket socket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private ConnectionState state = ConnectionState.INITIAL;
	
	public Connection( Socket socket ) {
		state = ConnectionState.STARTING;
		
		this.socket = socket;
		
		try {
			in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			out = new PrintWriter( socket.getOutputStream() );
			
			state = ConnectionState.READY;
		} catch ( Exception e ) {
			e.printStackTrace();
			
			state = ConnectionState.ERROR;
		}
	}
	
	public ConnectionState getConnectionState() {
		return state;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void run() {
		if ( state != ConnectionState.READY )
			return;
		else
			state = ConnectionState.RUNNING;
		
		try {
			String line = null;
			
			while ( state == ConnectionState.RUNNING && ( line = in.readLine() ) != null ) {
				Packet packet = Packet.reconstruct( line );
				
				if ( packet == null ) {
					close();
					
					break;
				}
			}
		} catch ( Exception e ) {
			if ( state != ConnectionState.CLOSED )
				e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void send( Packet packet ) {
		out.println( packet.toString() );
		out.flush();
	}
	
	public void close() {
		if ( state != ConnectionState.RUNNING )
			return;
		else
			state = ConnectionState.CLOSING;
		
		try {
			send( new Packet( Protocol.Disconnect ) );
			
			out.close();
			in.close();
			socket.close();
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			state = ConnectionState.CLOSED;
		}
	}
}
