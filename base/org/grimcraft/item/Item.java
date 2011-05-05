package org.grimcraft.item;

import org.grimcraft.event.ListenLogic;

public abstract class Item {
	private String name = "Misc Item";
	private String desc = "Misc Item";
	private ItemCollection collection = null;
	private Integer amount = 1;
	private Integer maxamount = 1;
	private ListenLogic logic = ListenLogic.Private;
	
	public final String getName() {
		return name;
	}
	
	public final void setName( String name ) {
		if ( name == null )
			return;
		
		this.name = name;
	}
	
	public final String getDescription() {
		return desc;
	}
	
	public final void setDescription( String desc ) {
		if ( desc == null )
			return;
		
		this.desc = desc;
	}
	
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
		
		amount = 0;
	}
	
	public final ItemCollection getCollection() {
		return collection;
	}
	
	public final void setCollection( ItemCollection newCollection ) {
		if ( newCollection == null ) {
			destroyItem();
			
			return;
		}
		
		if ( collection != null ) {
			collection.removeItem( this );
		}
		
		collection = newCollection;
		newCollection.addItem( this );
	}
	
	public abstract void onActivate();
	
	public String saveToString() {
		return null;
	}
	
	public void loadFromString( String string ) {}
}
