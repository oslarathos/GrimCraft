package org.grimcraft.event.ability;

import org.grimcraft.event.interfaces.EventListener;

public interface AbilityEventListener extends EventListener {
	public void onAbilityAdded( AbilityEvent event );
	
	public void onAbilityRemoved( AbilityEvent event );
}
