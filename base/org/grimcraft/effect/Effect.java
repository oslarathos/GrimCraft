package org.grimcraft.effect;

import org.grimcraft.event.EventTrigger;
import org.grimcraft.event.ListenLogic;
import org.grimcraft.event.effect.EffectEvent;

public abstract class Effect {
	private EffectRoster roster = null;
	private ListenLogic logic = ListenLogic.Private;
	
	protected final void init( EffectRoster roster ) {
		this.roster = roster;
	}
	
	public final EffectRoster getRoster() {
		return roster;
	}
	
	public final void setRoster( EffectRoster roster ) {
		if ( roster == null ) {
			destroyEffect();
			
			return;
		}
		
		
	}
	
	public final void destroyEffect() {
		if ( roster != null ) {
			EffectEvent event = new EffectEvent( EventTrigger.EFFECT_DESTROY, this );
			
			roster.trigger( event, new Object[] { this } );
		}
	}
	
	public final ListenLogic getListenLogic() {
		return logic;
	}
	
	public final void setListenLogic( ListenLogic logic ) {
		this.logic = logic;
	}
}
