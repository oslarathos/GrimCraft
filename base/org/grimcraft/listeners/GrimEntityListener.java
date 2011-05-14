package org.grimcraft.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.grimcraft.actor.Actor;
import org.grimcraft.event.actor.ActorDamageEvent;
import org.grimcraft.event.actor.ActorDamagedEvent;
import org.grimcraft.event.actor.ActorDeathEvent;
import org.grimcraft.module.ModuleManager;

public class GrimEntityListener extends EntityListener {
	private static GrimEntityListener instance = new GrimEntityListener();
	
	public static GrimEntityListener getInstance() {
		return instance;
	}
	
	private GrimEntityListener() {
		
	}
	
	public void onEntityDeath(EntityDeathEvent event) {
		if ( Actor.hasActor( event.getEntity() ) ) {
			ActorDeathEvent deathEvent = new ActorDeathEvent( Actor.getActor( event.getEntity() ) );
			
			ModuleManager.getInstance().trigger( deathEvent, deathEvent );
			Actor.getActor( event.getEntity() ).trigger( deathEvent, deathEvent );
		}
    }
	
	public void onEntityDamage( EntityDamageEvent event ) {
		Entity target = event.getEntity();
		
		if ( event instanceof EntityDamageByEntityEvent ) {
			EntityDamageByEntityEvent edbee = ( ( EntityDamageByEntityEvent ) event );
			
			Entity source = edbee.getDamager();
			
			if ( Actor.hasActor( source ) ) {
				Actor actor = Actor.getActor( source );
				ActorDamageEvent damageEvent = new ActorDamageEvent( actor, ( EntityDamageByEntityEvent ) event );
				
				ModuleManager.getInstance().trigger( damageEvent, damageEvent );
				actor.trigger( damageEvent, damageEvent );
			}
		}
		
		if ( Actor.hasActor( event.getEntity() ) ) {
			Actor actor = Actor.getActor( target );
			ActorDamagedEvent damagedEvent = new ActorDamagedEvent( actor, event );
			
			actor.trigger( damagedEvent, damagedEvent );
		}
    }
}
