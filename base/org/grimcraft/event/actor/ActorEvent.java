package org.grimcraft.event.actor;

import org.grimcraft.actor.Actor;
import org.grimcraft.event.Event;
import org.grimcraft.event.EventTrigger;

public class ActorEvent extends Event {
	private final Actor actor;
	
	public ActorEvent( EventTrigger trigger, Actor actor ) {
		super( trigger );
		
		this.actor = actor;
	}
	
	public final Actor getActor() {
		return actor;
	}
}
