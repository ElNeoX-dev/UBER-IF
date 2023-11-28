import java.util.Observable;

public class MapDataObservable extends Observable {

    public void dataChanged(List<Object> mapElems) {
        setChanged();
        notifyObservers(mapElems);
    }

}
