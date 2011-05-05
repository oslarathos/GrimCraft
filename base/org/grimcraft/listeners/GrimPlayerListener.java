package org.grimcraft.listeners;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.grimcraft.actor.Actor;

public class GrimPlayerListener extends PlayerListener {
	private static GrimPlayerListener instance = new GrimPlayerListener();
	
	public static GrimPlayerListener getInstance() {
		return instance;
	}
	
	
	private GrimPlayerListener() {
	}
	
	public void onPlayerJoin( PlayerJoinEvent event ) {
		Actor.getActor( event.getPlayer() );
	}
	
	public void onPlayerQuit( PlayerQuitEvent event ) {
		Actor.removeActor( event.getPlayer() );
	}
}
