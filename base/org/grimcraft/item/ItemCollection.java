package org.grimcraft.item;

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
import org.grimcraft.item.ItemContainer;
import org.grimcraft.module.ModuleManager;

public class ItemCollection implements EventListenerRoster {
	private ItemContainer container = null;
	private ArrayList< Item > items = new ArrayList< Item >();
	private HashMap< EventTrigger, ArrayList< Item > > triggers =
		new HashMap< EventTrigger, ArrayList< Item > >();
	
	public ItemCollection( ItemContainer container ) {
		this.container = container;
	}
	
	public ItemContainer getContainer() {
		return container;
	}
	
	public void addItem( Item item ) {
		if ( item == null || item.getAmount() < 1 || items.contains( item ) || item.isDestroyed() )
			return;
		
		if ( item.getCollection() != null && item.getCollection() != this )
			item.getCollection().removeItem( item );
			
		for ( Item existing : items ) {
			if ( existing.getClass() == item.getClass() ) {
				int amount = existing.getMaximumAmount() - existing.getAmount();
				
				if ( amount != 0 ) {
					if ( amount < item.getAmount() ) {
						existing.setAmount( existing.getMaximumAmount() );
						
						item.setAmount( item.getAmount() - amount );
					} else {
						amount -= item.getAmount();
						
						existing.setAmount( existing.getAmount() + amount );
						
						return;
					}
				}
			}
		}
		
		items.add( item );
		item.setCollection( this );
		
		if ( item instanceof EventListener ) {
			for ( EventTrigger trigger : ( ( EventListener ) item ).getTriggeredEvents() ) {
				if ( !trigger.getTriggerClass().isAssignableFrom( Item.class ) )
					continue;
				
				ArrayList< Item> list = triggers.get( trigger );
				
				if ( list == null ) {
					list = new ArrayList< Item >();
					
					triggers.put( trigger, list );
				}
				
				list.add( item );
			}
		}
		
		ItemEvent event = new ItemEvent( EventTrigger.ITEM_ADDED, item );
		
		getRootListener().trigger( event, event );
		ModuleManager.getInstance().trigger( event, event );
	}
	
	public void removeItem( Item item ) {
		if ( item == null || item.getAmount() == 0 || !items.contains( item ) )
			return;
		
		ItemEvent event = new ItemEvent( EventTrigger.ITEM_REMOVED, item );
		
		getRootListener().trigger( event, event );
		ModuleManager.getInstance().trigger( event, event );
		
		items.remove( item );
		item.setCollection( null );
	}
	
	public boolean hasItem( Item item ) {
		return items.contains( item );
	}
	
	public boolean hasItem( String name ) {
		for ( Item item : items ) {
			if ( item.getName().equalsIgnoreCase( name ) )
				return true;
		}
		
		return false;
	}
	
	public Item getItem( int index ) {
		if ( index < 0 || index > items.size() )
			return null;
		
		return items.get( index );
	}
	
	public Item getItem( String name ) {
		if ( name == null )
			return null;
		
		for ( Item item : items ) {
			if ( item.getName().equalsIgnoreCase( name ) )
				return item;
		}
		
		return null;
	}
	
	public ArrayList< Item > asList() {
		return new ArrayList< Item >( items );
	}
	
	@Override
	public void trigger( Event event, Object... params ) {
		if ( event == null || !triggers.containsKey( event.getTrigger() ) )
			return;
		
		if ( params == null )
			params = new Object[] {};
		
		EventTrigger trigger = event.getTrigger();
		ArrayList< Item > targets = new ArrayList< Item >();
		
		if ( event instanceof EffectEvent ) {
			if ( ( ( EffectEvent ) event ).getEffect().isDestroyed() )
				return;
			
			ListenLogic logic = ( ( EffectEvent ) event ).getEffect().getListenLogic();
			
			if ( logic == ListenLogic.ListenOnly || logic == ListenLogic.Private )
				return;
			else
				for ( Item item : triggers.get( trigger ) ) {
					ListenLogic itemlogic = item.getListenLogic();
					
					if ( itemlogic == ListenLogic.EmitOnly || itemlogic == ListenLogic.Private )
						continue;
					else
						targets.add( item );
				}
		}
		
		if ( event instanceof ItemEvent ) {
			Item source = ( ( ItemEvent ) event ).getItem();
			
			if ( !items.contains( source ) )
				return;
			
			ListenLogic logic = source.getListenLogic();
			
			if ( logic == ListenLogic.EmitOnly || logic == ListenLogic.Public ) {
				targets.addAll( triggers.get( trigger ) );
			} else {
				targets.add( source );
			}
		}
		
		for ( Item item : targets ) {
			try {
				Method m = item.getClass().getDeclaredMethod( trigger.getTriggerMethod(), trigger.getTriggerParameters() );
				
				if ( m == null )
					continue;
				
				m.invoke( item, params );
			} catch ( NoSuchMethodException nsme ) {
				System.out.println( "Tried to invoke non-existent '" + trigger.getTriggerMethod() + "' in " + item.getClass().getName() );
			} catch ( Exception e ) {
				e.printStackTrace();
				
				continue;
			}
		}
	}

	@Override
	public boolean isRootListener() {
		if ( container != null && container instanceof Actor )
			return false;
		
		return true;
	}

	@Override
	public EventListenerRoster getRootListener() {
		if ( container != null && container instanceof Actor )
			return ( Actor ) container;
		
		return this;
	}
}
