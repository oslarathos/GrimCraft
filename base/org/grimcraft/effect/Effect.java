package org.grimcraft.effect;

import org.grimcraft.event.EventTrigger;
import org.grimcraft.event.ListenLogic;
import org.grimcraft.event.effect.EffectEvent;
import org.grimcraft.misc.Saveable;
import org.grimcraft.misc.Visible;

public abstract class Effect extends Visible implements Saveable {
	private EffectRoster roster = null;
	private ListenLogic logic = ListenLogic.Private;
	private boolean destroyed = false;
		
	public final EffectRoster getRoster() {
		return roster;
	}
	
	public final void setRoster( EffectRoster newRoster ) {
		roster = newRoster;
	}
	
	public final void destroyEffect() {
		if ( roster != null ) {
			EffectEvent event = new EffectEvent( EventTrigger.EFFECT_DESTROY, this );
			
			roster.getActor().trigger( event, event );
			
			destroyed = true;
			
			roster.removeEffect( this );
		} else {
			destroyed = true;
		}
	}
	
	public final boolean isDestroyed() {
		return destroyed;
	}
	
	public final ListenLogic getListenLogic() {
		return logic;
	}
	
	public final void setListenLogic( ListenLogic logic ) {
		this.logic = logic;
	}
}
