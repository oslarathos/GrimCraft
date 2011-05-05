package org.grimcraft.event;

import org.grimcraft.effect.StackableEffect;
import org.grimcraft.event.actor.ActorDamageEvent;
import org.grimcraft.event.actor.ActorDamagedEvent;
import org.grimcraft.event.actor.ActorDeathEvent;
import org.grimcraft.event.actor.ActorEvent;
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
	EFFECT_REMOVED( EffectEventListener.class, "onEffectRemoved", new Class< ? >[] { EffectEvent.class } ),
	
	ITEM_ADDED( ItemEventListener.class, "onItemAdded", new Class< ? >[] { ItemEvent.class } ),
	ITEM_REMOVED( ItemEventListener.class, "onItemRemoved", new Class< ? >[] { ItemEvent.class } ),
	
	MODULE_ENABLE( ModuleEventListener.class, "onModuleEnabled", new Class< ? >[] { Module.class } ),
	MODULE_DISABLE( ModuleEventListener.class, "onModuleDisabled", new Class< ? >[] { Module.class } ),
	
	ACTOR_CREATE( ActorEventListener.class, "onActorCreated", new Class< ? >[] { ActorEvent.class } ),
	ACTOR_REMOVE( ActorEventListener.class, "onActorRemoved", new Class< ? >[] { ActorEvent.class } ),
	ACTOR_DEATH( ActorEventListener.class, "onActorDeath", new Class< ? >[] { ActorDeathEvent.class } ),
	ACTOR_DAMAGE( ActorEventListener.class, "onActorDamage", new Class< ? >[] { ActorDamageEvent.class } ),
	ACTOR_DAMAGED( ActorEventListener.class, "onActorDamaged", new Class< ? >[] { ActorDamagedEvent.class } );
	
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
