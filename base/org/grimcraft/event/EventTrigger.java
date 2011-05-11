package org.grimcraft.event;

import org.grimcraft.effect.StackableEffect;
import org.grimcraft.event.ability.AbilityEvent;
import org.grimcraft.event.ability.AbilityEventListener;
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
import org.grimcraft.event.player.PlayerCommandEvent;
import org.grimcraft.event.player.PlayerEventListener;
import org.grimcraft.module.Module;

public enum EventTrigger {
	
	EFFECT_CREATE( EffectEventListener.class, "onEffectCreated", EffectEvent.class ),
	EFFECT_DESTROY( EffectEventListener.class, "onEffectDestroyed", EffectEvent.class ),
	EFFECT_STACK( StackableEffect.class, "onEffectStack", StackableEffect.class ),
	
	EFFECT_ADDED( EffectEventListener.class, "onEffectAdded", EffectEvent.class ),
	EFFECT_REMOVED( EffectEventListener.class, "onEffectRemoved", EffectEvent.class ),
	
	ITEM_ADDED( ItemEventListener.class, "onItemAdded", ItemEvent.class ),
	ITEM_REMOVED( ItemEventListener.class, "onItemRemoved",  ItemEvent.class ),
	
	ABILITY_ADDED( AbilityEventListener.class, "onAbilityAdded", AbilityEvent.class ),
	ABILTIY_REMOVED( AbilityEventListener.class, "onAbilityRemoved", AbilityEvent.class ),
	
	MODULE_ENABLE( ModuleEventListener.class, "onModuleEnabled", Module.class ),
	MODULE_DISABLE( ModuleEventListener.class, "onModuleDisabled", Module.class ),
	
	PLAYER_COMMAND( PlayerEventListener.class, "onPlayerCommand", PlayerCommandEvent.class, String[].class ),
	
	ACTOR_CREATE( ActorEventListener.class, "onActorCreated", ActorEvent.class ),
	ACTOR_REMOVE( ActorEventListener.class, "onActorRemoved", ActorEvent.class ),
	
	ACTOR_DEATH( ActorEventListener.class, "onActorDeath", ActorDeathEvent.class ),
	ACTOR_DAMAGE( ActorEventListener.class, "onActorDamage", ActorDamageEvent.class ),
	ACTOR_DAMAGED( ActorEventListener.class, "onActorDamaged", ActorDamagedEvent.class );
	
	private final Class< ? > triggerClass;
	private final String triggerMethod;
	private final Class< ? >[] triggerParams;
	
	/**
	 * 
	 * @param triggerClass The class that the object derives the method from
	 * @param triggerMethod The name of the method to be invoked
	 * @param triggerParams The parameters of the method to be invoked
	 */
	private EventTrigger( Class< ? > triggerClass, String triggerMethod, Class< ? >... triggerParams ) {
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
