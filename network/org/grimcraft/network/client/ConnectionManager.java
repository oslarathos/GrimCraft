package org.grimcraft.network.client;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;

public class ConnectionManager extends Thread {
	private static ConnectionManager instance = new ConnectionManager();
	
	public static ConnectionManager getInstance() {
		return instance;
	}
	
	private ServerSocket server = null;
	private ConnectionState state = ConnectionState.INITIAL;
	private ArrayList< Connection > connections = new ArrayList< Connection >();
	
	private ConnectionManager() {
		state = ConnectionState.STARTING;
		
		try {
			server = new ServerSocket( 25564 );
			
			state = ConnectionState.READY;
		} catch ( Exception e ) {
			e.printStackTrace();
			
			state = ConnectionState.ERROR;
		}
	}
	
	public ConnectionState getConnectionState() {
		return state;
	}
	
	public boolean hasConnection( String address ) {
		for ( Connection conn : new ArrayList< Connection >( connections ) ) {
			if ( conn.getSocket().getInetAddress().toString().contains( address ) )
				return true;
		}
		
		return false;
	}
	
	public void run() {
		if ( server == null )
			return;
		
		state = ConnectionState.RUNNING;
		
		while ( state == ConnectionState.RUNNING ) {
			try {
				Socket socket = server.accept();
				
				if ( socket == null )
					continue;
				
				System.out.println( "Connection from " + socket.getInetAddress().getHostAddress() );
				
				Connection conn = new Connection( socket );
				
				if ( conn.getConnectionState() == ConnectionState.READY )
					conn.start();
			} catch ( Exception e ) {
				if  ( state != ConnectionState.CLOSED )
					e.printStackTrace();
			}
		}
	}
	
	public void close() {
		state = ConnectionState.CLOSING;
		
		try {
			server.close();
			
			state = ConnectionState.CLOSED;
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
