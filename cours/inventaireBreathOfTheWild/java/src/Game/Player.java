package Game;

import java.util.HashSet;
import java.util.Set;

import ObserverPattern.Event;
import ObserverPattern.Observable;

public class Player extends GameObject {
	
	private Set<Item> inventory;
	
	public Player() {
		inventory = new HashSet<Item>();
	}
	
	public void giveItem(Item item) {
		inventory.add(item);
		item.connect(this);
	}
	
	public void removeItem(Item item) {
		inventory.remove(item);
		item.disconnect(this);
	}
	
	@Override
	public void onEventDispatched(Observable origin, Event event) {
		if(event instanceof EventDestroyed) {
			if(origin instanceof Item && inventory.contains(origin)) {
				removeItem((Item) origin);
			}
		}
	}

	@Override
	public void update(int turn) {
		if(inventory.size() <= 0) {
			System.out.println("Le player est fauché.");
			destroy();
		}
	}

}
