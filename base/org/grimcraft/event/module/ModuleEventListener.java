package org.grimcraft.event.module;

import org.grimcraft.module.Module;

public interface ModuleEventListener {
	public void onModuleEnabled( Module module );
	
	public void onModuleDisabled( Module module );
}
