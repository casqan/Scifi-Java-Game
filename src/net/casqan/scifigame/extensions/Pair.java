package net.casqan.scifigame.extensions;

public class Pair <T, U> {
    public T first;
    public U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Pair other) {
            return other.first.equals(first) && other.second.equals(second);
        }
        return false;
    }
}
