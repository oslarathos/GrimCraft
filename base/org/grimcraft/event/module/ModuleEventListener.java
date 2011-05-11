package org.grimcraft.event.module;

import org.grimcraft.event.interfaces.EventListener;
import org.grimcraft.module.Module;

public interface ModuleEventListener extends EventListener {
	public void onModuleEnabled( Module module );
	
	public void onModuleDisabled( Module module );
}
