package org.grimcraft.listeners;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.grimcraft.actor.Actor;
import org.grimcraft.event.EventTrigger;
import org.grimcraft.event.actor.ActorEvent;
import org.grimcraft.event.player.PlayerCommandEvent;
import org.grimcraft.event.player.PlayerRightClickEvent;
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
		
		ActorEvent actorevent = new ActorEvent( EventTrigger.ACTOR_REMOVE, actor );
		
		ModuleManager.getInstance().trigger( actorevent, actorevent );
		actor.trigger( actorevent, actorevent );
		
		Actor.removeActor( event.getPlayer() );
	}
	
	public void onPlayerInteract( PlayerInteractEvent event ) {
		if ( event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
			PlayerRightClickEvent grimEvent = new PlayerRightClickEvent( event.getPlayer(), event.getClickedBlock() );
			
			ModuleManager.getInstance().trigger( grimEvent, grimEvent );
			Actor.getActor(event.getPlayer()).trigger( grimEvent, grimEvent );
			
			event.setCancelled( grimEvent.isCancelled() );
		}
    }
	
	public void onPlayerCommandPreprocess( PlayerCommandPreprocessEvent event ) {
		String[] split = event.getMessage().split( " " );
		
		PlayerCommandEvent playerEvent = new PlayerCommandEvent( event.getPlayer(), split[ 0 ].toLowerCase() );
    	
		String[] args = new String[ split.length - 1 ];
		
		for ( int i = 1; i < split.length; i++ ) {
			args[ i - 1 ] = split[ i ];
		}
		
    	ModuleManager.getInstance().trigger( playerEvent, playerEvent, args );
    	
    	event.setCancelled( playerEvent.isParsed() );
	}
}
