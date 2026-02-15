package model.combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class BattleLog<T> {

    private final List<T> events = new ArrayList<>();

    public void add(T e) {
        events.add(e);
    }

    public List<T> drain() {
        if (events.isEmpty())
            return Collections.emptyList();
        List<T> copy = new ArrayList<>(events);
        events.clear();
        return copy;

    }


}
