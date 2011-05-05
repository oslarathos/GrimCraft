package org.grimcraft.module;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.grimcraft.GrimCraft;
import org.grimcraft.event.Event;
import org.grimcraft.event.EventTrigger;
import org.grimcraft.event.interfaces.EventListener;
import org.grimcraft.event.interfaces.EventListenerRoster;
import org.grimcraft.event.module.ModuleEvent;

public class ModuleManager implements EventListenerRoster {
	private static ModuleManager instance = new ModuleManager();
	
	public static ModuleManager getInstance() {
		return instance;
	}
	
	private File folder = new File( GrimCraft.getInstance().getDataFolder(), "Module Data" );
	private ArrayList< Module > modules = new ArrayList< Module >();
	private HashMap< EventTrigger, ArrayList< Module > > triggers = new HashMap< EventTrigger, ArrayList< Module > >();
	
	private ModuleManager() {
		if ( !folder.exists() )
			folder.mkdir();
	}
	
	public File getModuleFolder() {
		return folder;
	}
	
	public Module loadModule( Class< ? extends Module > clazz ) {
		if ( clazz == null || clazz == Module.class )
			return null;
		
		try {
			Constructor< ? extends Module > cons = clazz.getConstructor( new Class< ? >[] {} );
			
			Module module = cons.newInstance( new Object[] {} );
			
			for ( Module existing : modules ) {
				if ( existing.getClass() == module.getClass() ) {
					System.out.println( "Duplicate module class detected: " + module.getClass().getCanonicalName() );
					
					return null;
				}
			}
			
			modules.add( module );
			
			if ( module instanceof EventListener ) {
				System.out.println( "Registering module events" );
				for ( EventTrigger trigger : ( ( EventListener ) module ).getTriggeredEvents() ) {
					if ( !trigger.getTriggerClass().isAssignableFrom( module.getClass() ) )
						continue;
					
					System.out.println( "Module registered event: " + trigger.name() );
					
					ArrayList< Module > list = triggers.get( trigger );
					
					if ( list == null ) {
						list = new ArrayList< Module >();
						
						triggers.put( trigger, list );
					}
					
					list.add( module );
				}
			}

			return module;
		} catch ( Exception e ) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public ArrayList< Module > getModules() {
		return new ArrayList< Module >( modules );
	}
	
	public void disableModule( String path ) {
		for ( Module module : modules ) {
			if ( module.getClass().getName().contains( path ) )
				disableModule( module );
		}
	}
	
	public void disableModule( Module module ) {
		System.out.println( "Disabled module: " + module.getName() );
		
		module.setEnabled( false );
		module.onDisable();
		
		ModuleEvent event = new ModuleEvent( EventTrigger.MODULE_DISABLE, module );
		
		trigger( event, event );
	}
	
	public void enableModule( String path ) {
		for ( Module module : modules ) {
			if ( module.getClass().getName().contains( path ) )
				enableModule( module );
		}
	}
	
	public void enableModule( Module module ) {
		System.out.println( "Enabling module: " + module.getName() );
		
		module.setEnabled( true );
		module.onEnable();
		
		ModuleEvent event = new ModuleEvent( EventTrigger.MODULE_ENABLE, module );
		
		trigger( event, event );
	}

	@Override
	public boolean isRootListener() {
		return true;
	}

	@Override
	public EventListenerRoster getRootListener() {
		return this;
	}

	@Override
	public void trigger( Event event, Object... params ) {
		if ( event == null || !triggers.containsKey( event.getTrigger() ) )
			return;
		
		EventTrigger trigger = event.getTrigger();
		
		if ( params == null )
			params = new Object[] {};
		
		for ( Module module : new ArrayList< Module >( triggers.get( event.getTrigger() ) ) ) {
			if ( !module.isEnabled() )
				continue;
			
			
			try {
				Method m = module.getClass().getDeclaredMethod( trigger.getTriggerMethod(), trigger.getTriggerParameters() );
				
				if ( m == null )
					continue;
				
				m.invoke( module, params );
			} catch ( NoSuchMethodException nsme ) {
				System.out.println( "Tried to invoke a missing method in the module named '" + module.getName() + "'" );
				
				continue;
			} catch ( Exception e ) {
				e.printStackTrace( System.out );
				
				continue;
			}
		}
	}
}
