package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TwoKeyHashMap<K1, K2, V> {
    private final HashMap<Pair<K1, K2 >, V> map = new HashMap<>();

    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    public void put(K1 key1, K2 key2, V value) {
        map.put(new Pair<>(key1, key2), value);
    }

    public V get(K1 key1, K2 key2) {
        return map.get(new Pair<>(key1, key2));
    }

    public V getById(K1 key1) {
        for (Pair<K1, K2> pair : map.keySet()) {
            if (pair.getFirst().equals(key1)) {
                return map.get(pair);
            }
        }
        return null;
    }

    public V getByQandR(K2 key2) {

        Pair<Integer, Integer> qAndR2 = (Pair<Integer, Integer>)key2;
        int q2 = qAndR2.getFirst();
        int r2 = qAndR2.getSecond();

        for (Pair<K1, K2> pair : map.keySet()) {
            Pair<Integer, Integer> qAndR1 = (Pair<Integer, Integer>)pair.getSecond();

            int q1 = qAndR1.getFirst();
            int r1 = qAndR1.getSecond();

            if (q1 == q2 && r1 == r2) {
                return map.get(pair);
            }
        }
        return null;
    }

    public List<V> getAllValues() {
        return new ArrayList<>(map.values());
    }
}