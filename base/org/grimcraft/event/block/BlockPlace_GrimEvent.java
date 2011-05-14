package org.grimcraft.event.block;

import org.bukkit.event.block.BlockPlaceEvent;
import org.grimcraft.actor.Actor;
import org.grimcraft.event.EventTrigger;

public class BlockPlace_GrimEvent extends BlockModify_GrimEvent {

	public BlockPlace_GrimEvent( Actor actor, BlockPlaceEvent event) {
		super( EventTrigger.BLOCK_PLACE, actor, event );
	}

	public BlockPlaceEvent getBukkitEvent() {
		return ( BlockPlaceEvent ) event;
	}
}
