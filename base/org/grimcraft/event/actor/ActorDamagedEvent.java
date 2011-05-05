package org.grimcraft.event.actor;

import org.bukkit.event.entity.EntityDamageEvent;
import org.grimcraft.actor.Actor;
import org.grimcraft.event.EventTrigger;

public class ActorDamagedEvent extends ActorEvent {
	private EntityDamageEvent event = null;
	
	public ActorDamagedEvent( Actor actor, EntityDamageEvent event ) {
		super( EventTrigger.ACTOR_DAMAGED, actor);
		
		this.event = event;
	}
	
	public EntityDamageEvent getBukkitEvent() {
		return event;
	}

}
