package Game;

import ObserverPattern.Event;
import ObserverPattern.Observable;

public class Item extends GameObject {
	
	private String name;
	private int durability;
	
	public Item(String name, int maxDurability) {
		this.name = name;
		durability = maxDurability;
	}

	@Override
	public void onEventDispatched(Observable origin, Event event) {
		
	}

	@Override
	public void update(int turn) {
		if(durability <= 0) {
			System.out.println("L'item "+name+" est périmé ou détruit.");
			destroy();
		}
		durability--;
	}
}
