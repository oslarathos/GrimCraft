package org.grimcraft.event.player;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.grimcraft.event.Event;
import org.grimcraft.event.EventTrigger;


public class PlayerRightClickEvent extends Event {
	private Player player;
	private Block block;
	private boolean cancelled;
	
	public PlayerRightClickEvent( Player player, Block block ) {
		super( EventTrigger.PLAYER_RIGHTCLICK );
	}

	public Player getPlayer() {
		return player;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public void cancel() {
		cancelled = true;
	}
	
	public Boolean isCancelled() {
		return cancelled;
	}
}
