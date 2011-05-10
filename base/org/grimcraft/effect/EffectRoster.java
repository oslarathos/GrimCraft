package org.grimcraft.effect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.grimcraft.actor.Actor;
import org.grimcraft.event.Event;
import org.grimcraft.event.EventTrigger;
import org.grimcraft.event.ListenLogic;
import org.grimcraft.event.effect.EffectEvent;
import org.grimcraft.event.interfaces.EventListener;
import org.grimcraft.event.interfaces.EventListenerRoster;
import org.grimcraft.event.item.ItemEvent;
import org.grimcraft.module.ModuleManager;

public class EffectRoster implements EventListenerRoster {
	private Actor actor = null;
	private ArrayList< Effect > effects = new ArrayList< Effect >();
	private HashMap< EventTrigger, ArrayList< Effect > > triggers =
		new HashMap< EventTrigger, ArrayList< Effect > >();
	
	public EffectRoster( Actor actor ) {
		this.actor = actor;
	}
	
	public Actor getActor() {
		return actor;
	}
	
	public void addEffect( Effect effect ) {
		if ( effect == null || effects.contains( effect ) || effect.isDestroyed() )
			return;
		
		if ( effect instanceof UniqueEffect ) {
			for ( Effect existing : new ArrayList< Effect >( effects ) ) {
				if ( existing.getClass() == effect.getClass() ) {
					( ( UniqueEffect ) existing ).onAttemptDuplicate( effect );
					
					return;
				}
			}
		}
		
		if ( effect instanceof StackableEffect ) {
			for ( Effect existing : new ArrayList< Effect >( effects ) ) {
				if ( existing.getClass() == effect.getClass() ) {
					( ( StackableEffect ) existing ).onStack( ( StackableEffect ) effect );
					
					return;
				}
			}
		}
		
		effects.add( effect );
		effect.setRoster( this );
		
		if ( effect instanceof EventListener ) {			
			for ( EventTrigger trigger : ( ( EventListener ) effect ).getTriggeredEvents() ) {
				if ( trigger.getTriggerClass().isAssignableFrom( effect.getClass() ) )
					continue;
				
				ArrayList< Effect > list = triggers.get( trigger );
				
				if ( list == null ) {
					list = new ArrayList< Effect >();
					
					triggers.put( trigger, list );
				}
				
				if ( !list.contains( effect ) )
					list.add( effect );
			}
		}
		
		EffectEvent event = new EffectEvent( EventTrigger.EFFECT_ADDED, effect );

		getActor().trigger( event, event );
		ModuleManager.getInstance().trigger( event, event );
	}
	
	public void removeEffect( Effect effect ) {
		if ( effect == null || !effects.contains( effect ) )
			return;
		
		effects.remove( effect );
		
		if ( effect instanceof EventListener ) {
			for ( EventTrigger trigger : ( ( EventListener ) effect ).getTriggeredEvents() ) {
				ArrayList< Effect > list = triggers.get( trigger );
				
				list.remove( effect );
				
				if ( list.size() == 0 )
					triggers.remove( trigger );
			}
		}
		
		EffectEvent event = new EffectEvent( EventTrigger.EFFECT_REMOVED, effect );
		
		getActor().trigger( event, event );
		ModuleManager.getInstance().trigger( event, event );
	}
	
	public Effect bindEffect( Class< ? extends Effect > effectClass ) {
		if ( effectClass == null )
			throw new NullPointerException( "Tried to bind a null class to an effect roster." );
		
		try {
			Constructor< ? extends Effect > construct = effectClass.getConstructor( new Class< ? >[] {} );
			
			if ( construct == null ) {
				System.out.println( "Could not locate the constructor for " + effectClass.getCanonicalName() );
				return null;
			}
			
			Effect effect = construct.newInstance( new Object[] {} );
			
			addEffect( effect );
			
			return effect;
		} catch ( Exception e ) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	@Override
	public void trigger( Event event, Object... params ) {
		if ( event == null || !triggers.containsKey( event.getTrigger() ) )
			return;
		
		if ( params == null )
			params = new Object[] {};
		
		EventTrigger trigger = event.getTrigger();
	
		ArrayList< Effect > targets = new ArrayList< Effect >();
		
		if ( event instanceof ItemEvent ) {
			if ( ( ( ItemEvent ) event ).getItem().isDestroyed() )
				return;
				
			ListenLogic logic = ( ( ItemEvent ) event ).getItem().getListenLogic();
			
			if ( logic == ListenLogic.ListenOnly || logic == ListenLogic.Private )
				return;
		}
		
		if ( event instanceof EffectEvent ) {
			Effect source = ( ( EffectEvent ) event ).getEffect();
			
			if ( source.isDestroyed() || !effects.contains( source ) )
				return;
			
			ListenLogic logic = source.getListenLogic();
						
			if ( logic == ListenLogic.EmitOnly || logic == ListenLogic.Public ) {
				targets.addAll( triggers.get( trigger ) );
			} else {
				targets.add( source );
			}
		} else {
			targets.addAll( triggers.get( trigger ) );
		}
		
		for ( Effect effect : targets ) {
			try {
				Method effectMethod = 
					effect.getClass().getDeclaredMethod(
						trigger.getTriggerMethod(),
						trigger.getTriggerParameters()
					);
				
				effectMethod.invoke( effect, params );
			} catch ( NoSuchMethodException nsme ) {
				System.out.println( "Tried to invoke non-existent " + trigger.getTriggerMethod() + " in " + effect.getClass().getName() );
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isRootListener() {
		return false;
	}

	@Override
	public EventListenerRoster getRootListener() {
		return actor;
	}

}
