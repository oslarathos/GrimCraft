package org.grimcraft.listeners;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.grimcraft.actor.Actor;
import org.grimcraft.event.EventTrigger;
import org.grimcraft.event.actor.ActorEvent;
import org.grimcraft.event.player.PlayerCommandEvent;
import org.grimcraft.module.ModuleManager;

public class GrimPlayerListener extends PlayerListener {
	private static GrimPlayerListener instance = new GrimPlayerListener();
	
	public static GrimPlayerListener getInstance() {
		return instance;
	}
	
	
	private GrimPlayerListener() {
	}
	
	public void onPlayerJoin( PlayerJoinEvent event ) {
		Actor actor = Actor.getActor( event.getPlayer() );
		
		ActorEvent actorevent = new ActorEvent( EventTrigger.ACTOR_CREATE, actor );
		
		ModuleManager.getInstance().trigger( actorevent, actorevent );
		actor.trigger( actorevent, actorevent );
	}
	
	public void onPlayerQuit( PlayerQuitEvent event ) {
		Actor actor = Actor.getActor( event.getPlayer() );
		
		Actor.removeActor( event.getPlayer() );
		
		ActorEvent actorevent = new ActorEvent( EventTrigger.ACTOR_REMOVE, actor );
		
		ModuleManager.getInstance().trigger( actorevent, actorevent );
		actor.trigger( actorevent, actorevent );
	}
}
