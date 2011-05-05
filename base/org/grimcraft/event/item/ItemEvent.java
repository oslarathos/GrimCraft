package org.grimcraft.event.item;

import org.grimcraft.event.Event;
import org.grimcraft.event.EventTrigger;
import org.grimcraft.item.Item;

public class ItemEvent extends Event {
	private final Item item;
	
	public ItemEvent( EventTrigger trigger, Item item ) {
		super( trigger );
		
		this.item = item;
	}

	public final Item getItem() {
		return item;
	}
}
