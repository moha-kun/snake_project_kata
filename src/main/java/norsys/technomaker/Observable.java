package norsys.technomaker;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    private List<Observer> observers;

    public Observable() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
        observer.update(this, null);
    }

    public void notifyObservers(Object obj) {
        observers.stream()
                .forEach(observer -> observer.update(this, obj));
    }

    public void notifyObservers() {
        notifyObservers(null);
    }

}
