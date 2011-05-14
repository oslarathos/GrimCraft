package org.grimcraft.event.block;

import org.bukkit.event.block.BlockBreakEvent;
import org.grimcraft.actor.Actor;
import org.grimcraft.event.EventTrigger;

public class BlockBreak_GrimEvent extends BlockModify_GrimEvent {

	public BlockBreak_GrimEvent( Actor actor, BlockBreakEvent event) {
		super( EventTrigger.BLOCK_BREAK, actor, event);
	}
	
	public BlockBreakEvent getBukkitEvent() {
		return ( org.bukkit.event.block.BlockBreakEvent ) event;
	}
}
