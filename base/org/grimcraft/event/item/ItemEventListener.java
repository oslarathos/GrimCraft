package org.grimcraft.event.item;

import org.grimcraft.event.interfaces.EventListener;
import org.grimcraft.item.ItemCollection;


public interface ItemEventListener extends EventListener {
	public void onItemCreated( ItemEvent event );
	
	public void onItemDestroyed( ItemEvent event );
	
	public void onItemChangeRoster( ItemEvent event, ItemCollection newRoster );
	
	public void onItemUsed( ItemEvent event );
}
