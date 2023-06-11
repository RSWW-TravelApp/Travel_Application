package api_gateway.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CircularList<T> {
    private List<T> list = new ArrayList<T>();
    private final Integer capacity;

    public CircularList(Integer capacity) {
        this.capacity = capacity;
    }

    public List<T> get() {
        return list;
    }

    public void add(T value) {
        list.add(0, value);
        if (list.size() > capacity) {
            list.remove(list.size() - 1);
        }
    }
}
