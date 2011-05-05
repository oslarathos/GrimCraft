package org.grimcraft.event.actor;

import org.grimcraft.event.interfaces.EventListener;

public interface ActorEventListener extends EventListener {
	public void onActorCreated( ActorEvent event );
	
	public void onActorRemoved( ActorEvent event );
	
	public void onActorDeath( ActorDeathEvent event );
	
	public void onActorDamage( ActorDamageEvent event );
	
	public void onActorDamaged( ActorDamagedEvent event );
}
