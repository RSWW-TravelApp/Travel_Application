package api_gateway.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CircularList<T> {
    private List<T> list = new ArrayList<T>();
    private final Integer capacity;
    private Integer pointer = 0;

    public CircularList(Integer capacity) {
        this.capacity = capacity;
    }

    public List<T> get() {
        return list;
    }

    public void add(T value) {
        if (!Objects.equals(list.size(), capacity)) {
            list.add(value);
            pointer = list.size();
            return;
        }
        if (Objects.equals(pointer, capacity)) {
            pointer = 0;
        }
        list.set(pointer, value);
        pointer++;
    }


}
