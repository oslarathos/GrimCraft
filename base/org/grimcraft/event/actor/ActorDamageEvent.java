package org.grimcraft.event.actor;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.grimcraft.actor.Actor;
import org.grimcraft.event.EventTrigger;

public class ActorDamageEvent extends ActorEvent {
	private EntityDamageByEntityEvent event = null;
	
	public ActorDamageEvent( Actor actor, EntityDamageByEntityEvent event ) {
		super( EventTrigger.ACTOR_DAMAGE, actor);
		
		this.event = event;
	}
	
	public EntityDamageByEntityEvent getBukkitEvent() {
		return event;
	}

}
