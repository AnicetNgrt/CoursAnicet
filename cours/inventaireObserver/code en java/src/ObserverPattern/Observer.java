package ObserverPattern;

public interface Observer {
	public void onEventDispatched(Observable origin, Event event);
}
