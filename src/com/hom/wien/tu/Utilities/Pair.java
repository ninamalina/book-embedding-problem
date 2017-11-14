package com.hom.wien.tu.Utilities;

import java.util.Objects;

/**
 * Created by davorsafranko on 11/12/17.
 */
public class Pair {
    private int first;
    private int second;

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if ( !(obj instanceof Pair)) return false;
        Pair other = (Pair)obj;

        return (other.first == this.first && other.second == this.second) || (other.first == this.second && other.second == this.first);
    }
}
