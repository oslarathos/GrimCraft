package org.grimcraft.event.module;

import org.grimcraft.event.Event;
import org.grimcraft.event.EventTrigger;
import org.grimcraft.module.Module;

public class ModuleEvent extends Event {
	private final Module module;
	
	public ModuleEvent( EventTrigger trigger, Module module ) {
		super( trigger );

		this.module = module;
	}
	
	public final Module getModule() {
		return module;
	}
}
