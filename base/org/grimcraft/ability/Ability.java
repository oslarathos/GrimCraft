package org.grimcraft.ability;

import org.grimcraft.misc.Saveable;
import org.grimcraft.misc.Visible;

public abstract class Ability extends Visible implements Saveable {
	private AbilitySet abilities = null;
	
	public AbilitySet getAbilitySet() {
		return abilities;
	}
	
	public void setAbilitySet( AbilitySet abilityset ) {
		abilities = abilityset;
	}
	
	public abstract void onActivate();
}
