package warzone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents observable in Observer pattern.
 *
 */
public class Observable {
	/**
	 * list of Observers
	 */
	private List<Observer> d_observers = new ArrayList<Observer>();
	
	/**
	 * This method can attach observers to the observer list in it.
	 * @param p_observer the observer that should be inserted
	 */
	public void attach(Observer p_observer) {
		d_observers.add(p_observer);
	}
	
	/**
	 * This method can detach observers from the observer list in it.
	 * @param p_observer the observer that should be removed
	 */
	public void detach(Observer p_observer) {
		if(!d_observers.isEmpty())
			d_observers.remove(p_observer);
	}
	
	/**
	 * This method will notify all observers in the list.
	 * @param p_observable the observable instance
	 */
	public void notify(Observable p_observable) {
		for(Observer l_observer : d_observers) {
			l_observer.update(p_observable);
		}
	}
	
	
}

