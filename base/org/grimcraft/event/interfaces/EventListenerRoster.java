package org.grimcraft.event.interfaces;

import org.grimcraft.event.Event;

public interface EventListenerRoster {
	public boolean isRootListener();
	
	public EventListenerRoster getRootListener();
	
	public void trigger( Event event, Object... params );
}
