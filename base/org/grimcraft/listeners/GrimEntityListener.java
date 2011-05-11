package org.grimcraft.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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
	
	public void onEntityDamage( EntityDamageEvent event ) {
		Entity target = event.getEntity();
		Entity source = null;
		
		if ( event instanceof EntityDamageByEntityEvent ) {
			EntityDamageByEntityEvent edbee = ( ( EntityDamageByEntityEvent ) event );
			
			source = edbee.getDamager();
			
			if ( Actor.hasActor( source ) ) {
				Actor actor = Actor.getActor( source );
				ActorDamageEvent damageEvent = new ActorDamageEvent( actor, ( EntityDamageByEntityEvent ) event );
								
				actor.trigger( damageEvent, damageEvent );
			}
		}
		
		if ( Actor.hasActor( event.getEntity() ) ) {
			Actor actor = Actor.getActor( target );
			ActorDamagedEvent damagedEvent = new ActorDamagedEvent( actor, event );
			
			actor.trigger( damagedEvent, damagedEvent );
			
			if ( target instanceof LivingEntity ) {
				LivingEntity livingTarget = ( LivingEntity ) target;
				
				if ( !event.isCancelled() && event.getDamage() >= livingTarget.getHealth() ) {
					ActorDeathEvent deathEvent = null;
					
					if ( source != null ) {
						deathEvent = new ActorDeathEvent( actor, Actor.getActor( source ), event );
					} else {
						deathEvent = new ActorDeathEvent( actor, event );
					}
					
					actor.trigger( deathEvent, deathEvent );
					ModuleManager.getInstance().trigger( deathEvent, deathEvent );
					
					if ( deathEvent.isCancelled() )
						event.setCancelled( true );
				}
			}
		}
    }
}
