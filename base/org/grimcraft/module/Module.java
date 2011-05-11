package org.grimcraft.module;

import java.io.File;

public abstract class Module {	
	private String name = "Module Name";
	private String desc = "Module Description";
	private boolean enabled = false;
	
	public final String getName() {
		return name;
	}
	
	public final void setName( String name ) {
		this.name = name;
	}
	
	public final String getDescription() {
		return desc;
	}
	
	public final void setDescription( String desc ) {
		this.desc = desc;
	}
	
	public final File getDataFolder() {
		return new File( ModuleManager.getInstance().getModuleFolder(), getClass().getName() );
	}
	
	public final boolean isEnabled() {
		return enabled;
	}
	
	public final void setEnabled( boolean state ) {
		this.enabled = state;
	}
	
	public abstract void onEnable();
	
	public abstract void onDisable();
}
