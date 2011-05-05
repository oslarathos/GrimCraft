package org.grimcraft.event;

import org.grimcraft.actor.Actor;
import org.grimcraft.effect.StackableEffect;
import org.grimcraft.event.actor.ActorEventListener;
import org.grimcraft.event.effect.EffectEvent;
import org.grimcraft.event.effect.EffectEventListener;
import org.grimcraft.event.item.ItemEvent;
import org.grimcraft.event.item.ItemEventListener;
import org.grimcraft.event.module.ModuleEventListener;
import org.grimcraft.module.Module;

public enum EventTrigger {
	EFFECT_CREATE( EffectEventListener.class, "onEffectCreated", new Class< ? >[] { EffectEvent.class } ),
	EFFECT_DESTROY( EffectEventListener.class, "onEffectDestroyed", new Class< ? >[] { EffectEvent.class } ),
	EFFECT_STACK( StackableEffect.class, "onStack", new Class< ? >[] { StackableEffect.class } ),
	
	EFFECT_ADDED( EffectEventListener.class, "onEffectAdded", new Class< ? >[] { EffectEvent.class } ),
	EFFECT_REMOVE( EffectEventListener.class, "onEffectRemoved", new Class< ? >[] { EffectEvent.class } ),
	
	ITEM_ADDED( ItemEventListener.class, "onItemAdded", new Class< ? >[] { ItemEvent.class } ),
	ITEM_REMOVED( ItemEventListener.class, "onItemRemoved", new Class< ? >[] { ItemEvent.class } ),
	
	MODULE_ENABLE( ModuleEventListener.class, "onModuleEnabled", new Class< ? >[] { Module.class } ),
	MODULE_DISABLE( ModuleEventListener.class, "onModuleDisabled", new Class< ? >[] { Module.class } ),
	
	ACTOR_CREATE( ActorEventListener.class, "onActorCreated", new Class< ? >[] { Actor.class } ),
	ACTOR_REMOVE( ActorEventListener.class, "onActorRemoved", new Class< ? >[] { Actor.class } );
	
	private final Class< ? > triggerClass;
	private final String triggerMethod;
	private final Class< ? >[] triggerParams;
	
	private EventTrigger( Class< ? > triggerClass, String triggerMethod, Class< ? >[] triggerParams ) {
		this.triggerClass = triggerClass;
		this.triggerMethod = triggerMethod;
		this.triggerParams = triggerParams;
	}
	
	public final Class< ? > getTriggerClass() {
		return triggerClass;
	}

	public final String getTriggerMethod() {
		return triggerMethod;
	}
	
	public final Class< ? >[] getTriggerParameters() {
		return triggerParams;
	}
}
