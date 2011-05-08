package org.grimcraft.event;

import org.grimcraft.event.interfaces.EventListenerRoster;

public class TriggerSwitch {
	public static void trigger( Event event, Object[] params, EventListenerRoster... eventListeners  ) {
		for ( EventListenerRoster listener : eventListeners ) {
			listener.trigger( event, params );
		}
	}
}
