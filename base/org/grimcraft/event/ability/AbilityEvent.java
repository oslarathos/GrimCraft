package org.grimcraft.event.ability;

import org.grimcraft.ability.Ability;
import org.grimcraft.event.Event;
import org.grimcraft.event.EventTrigger;

public class AbilityEvent extends Event {
	private Ability ability;
	
	public AbilityEvent( EventTrigger trigger, Ability ability ) {
		super( trigger );
		
		this.ability = ability;
	}

	public Ability getAbility() {
		return ability;
	}
}
