package org.grimcraft.listeners;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.grimcraft.actor.Actor;
import org.grimcraft.event.block.BlockBreak_GrimEvent;
import org.grimcraft.event.block.BlockPlace_GrimEvent;
import org.grimcraft.module.ModuleManager;

public class GrimBlockListener extends BlockListener {
	private static GrimBlockListener instance = new GrimBlockListener();
	
	public static GrimBlockListener getInstance() {
		return instance;
	}
	
	private GrimBlockListener() {
		
	}
	
	public void onBlockPlace( BlockPlaceEvent event ) {
		if ( !Actor.hasActor( event.getPlayer() ) )
			return;
		
		Actor actor = Actor.getActor( event.getPlayer() );
		
		BlockPlace_GrimEvent grim_event = new BlockPlace_GrimEvent( actor, event );
		
		ModuleManager.getInstance().trigger( grim_event, grim_event );
		actor.trigger( grim_event, grim_event );
    }
	
	public void onBlockBreak( BlockBreakEvent event ) {
		if ( !Actor.hasActor( event.getPlayer() ) )
			return;
		
		Actor actor = Actor.getActor( event.getPlayer() );
		
		BlockBreak_GrimEvent grim_event = new BlockBreak_GrimEvent( actor, event );
		
		ModuleManager.getInstance().trigger( grim_event, grim_event );
		actor.trigger( grim_event, grim_event );
    }
}
