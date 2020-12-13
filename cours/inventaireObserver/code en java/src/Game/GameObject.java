package Game;

import ObserverPattern.Observable;
import ObserverPattern.Observer;

public abstract class GameObject extends Observable implements Observer {
	
	public abstract void update(int turn);
	
	public void destroy() {
		dispatchEvent(new EventDestroyed());
	}
}
