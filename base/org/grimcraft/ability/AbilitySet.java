package org.grimcraft.ability;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.grimcraft.actor.Actor;
import org.grimcraft.effect.Effect;
import org.grimcraft.event.Event;
import org.grimcraft.event.EventTrigger;
import org.grimcraft.event.ListenLogic;
import org.grimcraft.event.ability.AbilityEvent;
import org.grimcraft.event.effect.EffectEvent;
import org.grimcraft.event.interfaces.EventListener;
import org.grimcraft.event.interfaces.EventListenerRoster;
import org.grimcraft.event.item.ItemEvent;
import org.grimcraft.item.Item;
import org.grimcraft.module.ModuleManager;

public class AbilitySet implements EventListenerRoster {
	private final Actor actor;
	private ArrayList< Ability > abilities = new ArrayList< Ability >();
	private HashMap< EventTrigger, ArrayList< Ability > > triggers = new HashMap< EventTrigger, ArrayList< Ability > >();
	
	public AbilitySet( Actor actor ) {
		this.actor = actor;
	}
	
	public Actor getActor() {
		return actor;
	}
	
	public void addAbility( Ability ability ) {
		if ( ability == null || abilities.contains( ability ) )
			return;
		
		if ( ability.getAbilitySet() != null )
			ability.getAbilitySet().removeAbility( ability );
		
		abilities.add( ability );
		ability.setAbilitySet( this );
		
		if ( ability instanceof EventListener ) {
			for ( EventTrigger trigger : ( ( EventListener ) ability ).getTriggeredEvents() ) {
				if ( trigger.getTriggerClass().isAssignableFrom( ability.getClass() ) )
					continue;
				
				ArrayList< Ability > list = triggers.get( trigger );
				
				if ( list == null ) {
					list = new ArrayList< Ability >();
					
					triggers.put( trigger, list );
				}
				
				if ( !list.contains( ability ) )
					list.add( ability );
			}
		}
		
		AbilityEvent event = new AbilityEvent( EventTrigger.ABILITY_ADDED, ability );
		
		actor.trigger( event, event );
		ModuleManager.getInstance().trigger( event, event );
	}
	
	public void removeAbility( Ability ability ) {
		if ( ability == null || !abilities.contains( ability ) )
			return;
		
		abilities.remove( ability );
		ability.setAbilitySet( null );
		
		if ( ability instanceof EventListener ) {
			for ( EventTrigger trigger : ( ( EventListener ) ability ).getTriggeredEvents() ) {
				ArrayList< Ability > list = triggers.get( trigger );
				
				list.remove( ability );
				
				if ( list.size() == 0 )
					triggers.remove( trigger );
			}
		}
		
		AbilityEvent event = new AbilityEvent( EventTrigger.ABILTIY_REMOVED, ability );
		
		actor.trigger( event, event );
		ModuleManager.getInstance().trigger( event, event );
	}
	
	public ArrayList< Ability > getAbilities() {
		return new ArrayList< Ability >( abilities );
	}
	
	public Ability getAbility( String name ) {
		for ( Ability ability : abilities ) {
			if ( ability.getName().equalsIgnoreCase( name ) )
				return ability;
		}
		
		return null;
	}
	
	@Override
	public boolean isRootListener() {
		return false;
	}

	@Override
	public EventListenerRoster getRootListener() {
		return actor;
	}

	@Override
	public void trigger( Event event, Object... params ) {
		if ( event == null || !triggers.containsKey( event.getTrigger() ) )
			return;
		
		EventTrigger trigger = event.getTrigger();
		
		if ( event instanceof EffectEvent ) {
			Effect effect = ( ( EffectEvent ) event ).getEffect();
			
			if ( effect.isDestroyed() )
				return;
			
			if ( effect.getListenLogic() == ListenLogic.ListenOnly || effect.getListenLogic() == ListenLogic.Private ) 
				return;
		}
		
		if ( event instanceof ItemEvent ) {
			Item item = ( ( ItemEvent ) event ).getItem();
			
			if ( item.isDestroyed() )
				return;
			
			if ( item.getListenLogic() == ListenLogic.ListenOnly || item.getListenLogic() == ListenLogic.Private )
				return;
		}
		
		for( Ability ability : triggers.get( trigger ) ) {
			try {
				
				Method method = ability.getClass().getDeclaredMethod( trigger.getTriggerMethod(), trigger.getTriggerParameters() );
				
				method.invoke( ability, params );
			} catch ( NoSuchMethodException nsme ) {
				System.out.println( "Tried to invoke missing method in: " + ability.getClass().getName() );
			} catch ( Exception e ){
				e.printStackTrace();
			}
		}
	}

}
