package org.grimcraft.event.actor;

import org.grimcraft.actor.Actor;
import org.grimcraft.event.interfaces.EventListener;

public interface ActorEventListener extends EventListener {
	public void onActorCreated( Actor actor );
	
	public void onActorRemoved( Actor actor );
}
