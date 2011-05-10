package org.grimcraft.event.effect;

import org.grimcraft.effect.Effect;
import org.grimcraft.event.interfaces.EventListener;

public interface EffectEventListener extends EventListener {
	public void onEffectCreated( Effect effect );
	
	public void onEffectDestroyed( Effect effect );
	
	public void onEffectAdded( EffectEvent event );
	
	public void onEffectRemoved( EffectEvent event );
}
