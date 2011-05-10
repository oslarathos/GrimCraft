package org.grimcraft.misc;

public interface Saveable {
	public String saveToString();
	
	public void loadFromString( String data );
}
