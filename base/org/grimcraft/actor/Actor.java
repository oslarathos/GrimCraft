package org.grimcraft.actor;

import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.grimcraft.effect.EffectRoster;
import org.grimcraft.event.Event;
import org.grimcraft.event.EventTrigger;
import org.grimcraft.event.actor.ActorEvent;
import org.grimcraft.event.interfaces.EventListenerRoster;
import org.grimcraft.item.ItemCollection;
import org.grimcraft.item.ItemContainer;
import org.grimcraft.module.ModuleManager;

public class Actor implements ItemContainer, EventListenerRoster {
	private static HashMap< Entity, Actor > actors = new HashMap<Entity, Actor>();
	
	public static boolean hasActor( Entity entity ) {
		return actors.containsKey( entity );
	}
	
	public static Actor getActor( Entity entity ) {
		if ( hasActor( entity ) )
			return actors.get( entity );
		
		Actor actor = new Actor( entity );
		actors.put( entity, actor );
		
		ActorEvent event = new ActorEvent( EventTrigger.ACTOR_CREATE, actor );
		
		actor.getRootListener().trigger( event );
		ModuleManager.getInstance().trigger( event, actor );
		
		return actor;
	}
	
	public static void removeActor( Entity entity ) {
		if ( hasActor( entity ) ) {
			Actor actor = getActor( entity );
						
			ActorEvent event = new ActorEvent( EventTrigger.ACTOR_REMOVE, actor );
			
			actor.trigger( event );
			ModuleManager.getInstance().trigger( event, actor );
			
			actors.remove( entity );
		}
	}
	
	private Entity entity = null;
	private EffectRoster effects = new EffectRoster( this );
	private ItemCollection items = new ItemCollection( this );
	
	private Actor( Entity entity ) {
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public EffectRoster getEffects() {
		return effects;
	}
	
	public ItemCollection getInventory() {
		return items;
	}

	@Override
	public void trigger( Event event, Object... params ) {
		effects.trigger( event, params );
		items.trigger( event, params );
	}

	@Override
	public boolean isRootListener() {
		return true;
	}

	@Override
	public EventListenerRoster getRootListener() {
		return this;
	}
}
