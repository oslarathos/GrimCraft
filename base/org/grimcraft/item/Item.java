package org.grimcraft.item;

import org.grimcraft.event.ListenLogic;
import org.grimcraft.misc.Saveable;
import org.grimcraft.misc.Visible;

public abstract class Item extends Visible implements Saveable {
	private ItemCollection collection = null;
	private Integer amount = 1;
	private Integer maxamount = 1;
	private ListenLogic logic = ListenLogic.Private;
	private boolean destroyed = false;
	
	public final Integer getMaximumAmount() {
		return maxamount;
	}
	
	public void setMaximumAmount( Integer maxamount ) {
		this.maxamount = maxamount;
	}
	
	public final Integer getAmount() {
		return amount;
	}
	
	public final void setAmount( Integer amount ) {
		if ( amount == null )
			return;
		else if ( amount == 0 )
			destroyItem();
		else if ( amount > getMaximumAmount() ) {
			amount -= getMaximumAmount() - amount;
			this.amount = getMaximumAmount();
			
			try {
				Item item = this.getClass().getConstructor().newInstance();
				
				item.setAmount( amount );
				
				
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		else
			this.amount = amount;
	}
	
	public final ListenLogic getListenLogic() {
		return logic;
	}
	
	public final void setListenLogic( ListenLogic logic ) {
		this.logic = logic;
	}
	
	public final void destroyItem() {
		if ( collection != null )
			collection.removeItem( this );
		
		destroyed = true;
	}
	
	public final boolean isDestroyed() {
		return destroyed;
	}
	
	public final ItemCollection getCollection() {
		return collection;
	}
	
	public final void setCollection( ItemCollection newCollection ) {
		collection = newCollection;
	}
	
	public abstract void onActivate();
	
	public String saveToString() {
		return null;
	}
	
	public void loadFromString( String string ) {}
}
