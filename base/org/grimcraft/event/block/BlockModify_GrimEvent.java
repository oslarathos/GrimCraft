package org.grimcraft.event.block;

import org.bukkit.event.block.BlockEvent;
import org.grimcraft.actor.Actor;
import org.grimcraft.event.Event;
import org.grimcraft.event.EventTrigger;

public class BlockModify_GrimEvent extends Event {
	protected BlockEvent event;
	private Actor actor;
	
	public BlockModify_GrimEvent( EventTrigger trigger, Actor actor, BlockEvent event ) {
		super( trigger );
		
		this.event = event;
		this.actor = actor;
	}
	
	public BlockEvent getBlockEvent() {
		return event;
	}

	public Actor getActor() {
		return actor;
	}
}
