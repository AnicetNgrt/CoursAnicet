package Game;

import java.util.HashSet;
import java.util.Set;

import ObserverPattern.Event;
import ObserverPattern.Observable;
import ObserverPattern.Observer;

public class Game implements Observer {
	
	private int turn;
	private Set<GameObject> objects;
	
	public Game() {
		turn = 0;
		objects = new HashSet<GameObject>();
	}
	
	public void addObject(GameObject o) {
		objects.add(o);
		o.connect(this);
	}
	
	public void removeObject(GameObject o) {
		objects.remove(o);
		o.disconnect(this);
	}

	public void onEventDispatched(Observable origin, Event event) {
		if(event instanceof EventDestroyed) {
			if(origin instanceof GameObject) {
				removeObject((GameObject) origin);
			}
		}
	}
	
	public boolean isFinished() {
		return objects.size() <= 0;
	}
	
	public void nextTurn() {
		System.out.println("Début du tour "+turn);
		
		Set<GameObject> objects = new HashSet<GameObject>(this.objects);
		
		for(GameObject o:objects) {
			o.update(turn);
		}
		
		turn++;
	}
	
}
