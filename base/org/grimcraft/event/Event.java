package org.grimcraft.event;


public class Event {
	private final EventTrigger trigger;
	
	public Event( EventTrigger trigger ) {
		this.trigger = trigger;
	}
	
	public final EventTrigger getTrigger() {
		return trigger;
	}
	
	
}
