package org.grimcraft.event.effect;

import org.grimcraft.effect.Effect;
import org.grimcraft.event.Event;
import org.grimcraft.event.EventTrigger;

public class EffectEvent extends Event {
	private final Effect effect;
	
	public EffectEvent( EventTrigger trigger, Effect effect ) {
		super(trigger);

		this.effect = effect;
	}
	
	public final Effect getEffect() {
		return effect;
	}
}
