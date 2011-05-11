package org.grimcraft.event.player;

import org.bukkit.entity.Player;
import org.grimcraft.event.Event;
import org.grimcraft.event.EventTrigger;

public class PlayerCommandEvent extends Event {
	private Player player;
	private String command;
	private boolean parsed = false;
	
	public PlayerCommandEvent( Player player, String command ) {
		super( EventTrigger.PLAYER_COMMAND );
		
		this.player = player;
		this.command = command;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setParsed() {
		parsed = true;
	}
	
	public boolean isParsed() {
		return parsed;
	}
}
