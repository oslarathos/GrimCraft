package org.grimcraft.event.player;

import org.grimcraft.event.interfaces.EventListener;


public interface PlayerEventListener extends EventListener {
	public void onPlayerCommand( PlayerCommandEvent event, String[] args );
}
