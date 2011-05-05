package org.grimcraft.effect;

import org.grimcraft.event.EventTrigger;
import org.grimcraft.event.ListenLogic;
import org.grimcraft.event.effect.EffectEvent;

public abstract class Effect {
	private String name = "Misc Effect";
	private String desc = "Misc Effect";
	private EffectRoster roster = null;
	private ListenLogic logic = ListenLogic.Private;
	private boolean destroyed = false;
	
	protected final void init( EffectRoster roster ) {
		this.roster = roster;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName( String name ) {
		this.name = name;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public void setDescription( String desc ) {
		this.desc = desc;
	}
	
	public final EffectRoster getRoster() {
		return roster;
	}
	
	public final void setRoster( EffectRoster newRoster ) {
		if ( newRoster == null ) {
			destroyEffect();
			
			return;
		}
		
		if ( roster != null ) {
			roster.removeEffect( this );
		}
		
		roster = newRoster;
		newRoster.addEffect( this );
	}
	
	public final void destroyEffect() {
		if ( roster != null ) {
			EffectEvent event = new EffectEvent( EventTrigger.EFFECT_DESTROY, this );
			
			roster.getActor().trigger( event, event );
			
			destroyed = true;
			
			roster.removeEffect( this );
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
