package pl.streamsoft.util;

public interface Observable {
    void addObserver(Observer observer);
    void remove(Observer observer);
    void notifyObservers(Object object);
}
