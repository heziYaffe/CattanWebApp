package entities;

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


}
