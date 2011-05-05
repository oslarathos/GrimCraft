package org.grimcraft.event.actor;

import org.bukkit.event.entity.EntityDamageEvent;
import org.grimcraft.actor.Actor;
import org.grimcraft.event.EventTrigger;

public class ActorDeathEvent extends ActorEvent {
	private Actor killer = null;
	private EntityDamageEvent ede;
	private boolean cancelled = false;
	
	public ActorDeathEvent( Actor actor, EntityDamageEvent ede ) {
		super( EventTrigger.ACTOR_DEATH, actor);
		
		this.ede = ede;
	}
	
	public ActorDeathEvent( Actor actor, Actor killer, EntityDamageEvent ede ) {
		super( EventTrigger.ACTOR_DEATH, actor );
		
		this.killer = killer;
		this.ede = ede;
	}
	
	public EntityDamageEvent getBukkitEvent() {
		return ede;
	}
	
	public Actor getKiller() {
		return killer;
	}
	
	public void cancel() {
		cancelled = true;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
}
