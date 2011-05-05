package org.grimcraft;

import java.util.ArrayList;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.grimcraft.listeners.GrimPlayerListener;
import org.grimcraft.module.Module;
import org.grimcraft.module.ModuleManager;

public class GrimCraft extends JavaPlugin {
	private static GrimCraft instance = null;
	
	public static GrimCraft getInstance() {
		return instance;
	}
	
	@Override
	public void onDisable() {
		for ( Module module : ModuleManager.getInstance().getModules() ) {
			ModuleManager.getInstance().disableModule( module );
		}
	}

	@Override
	public void onEnable() {
		instance = this;
		
		if ( !getDataFolder().exists() )
			getDataFolder().mkdir();
		
		ResourceLoader.getLoader().loadResources();
		
		ArrayList< Module > modules = ModuleManager.getInstance().getModules();
		
		if ( modules.size() == 0 ) {
			System.out.println( "No GrimCraft modules have been loaded." );
		} else {
			System.out.println( "A total of " + modules.size() + " GrimCraft modules have been loaded." );
		}
		
		registerEvent( Type.PLAYER_JOIN, GrimPlayerListener.getInstance() );
		registerEvent(Type.PLAYER_QUIT, GrimPlayerListener.getInstance() );
	}
	
	public void registerEvent( Type type, Listener listener) {
		System.out.println( type.name() + " => " + listener.getClass().getName() );
		
		getServer().getPluginManager().registerEvent( type, listener, Priority.High, this);
	}
}
