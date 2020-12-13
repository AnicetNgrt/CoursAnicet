package ObserverPattern;

import java.util.HashSet;
import java.util.Set;

public class Observable {
	
	private Set<Observer> subscribers;
	
	public Observable() {
		subscribers = new HashSet<Observer>();
	}
	
	public void connect(Observer observer) {
		subscribers.add(observer);
	}
	
	public void disconnect(Observer observer) {
		subscribers.remove(observer);
	}
	
	public void dispatchEvent(Event event) {
		Set<Observer> subscribers = new HashSet<Observer>(this.subscribers);
		
		for(Observer o:subscribers) {
			o.onEventDispatched(this, event);
		}
	}
}
