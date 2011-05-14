package org.grimcraft.event.block;

import org.bukkit.event.block.BlockPlaceEvent;
import org.grimcraft.event.interfaces.EventListener;

public interface BlockEventListener extends EventListener {
	public void onBlockPlace( BlockPlaceEvent event );
	
	public void onBlockBreak( BlockBreak_GrimEvent event );
}
