package org.grimcraft.event.actor;

import org.grimcraft.actor.Actor;
import org.grimcraft.event.EventTrigger;

public class ActorDeathEvent extends ActorEvent {
	private Actor killer = null;
	
	public ActorDeathEvent( Actor actor ) {
		super( EventTrigger.ACTOR_DEATH, actor);
	}
	
	public Actor getKiller() {
		return killer;
	}
}
