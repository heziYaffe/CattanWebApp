package entities;

import java.util.Objects;

public class Pair<E, H> {
    private final E first;
    private final H second;

    public Pair(E first, H second) {
        this.first = first;
        this.second = second;
    }



    public E getFirst() {
        return first;
    }

    public H getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }



}
