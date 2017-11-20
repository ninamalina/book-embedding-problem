package com.hom.wien.tu.Utilities;

import java.util.Comparator;
import java.util.List;

public class ArrayIndexComparator implements Comparator<Integer>{
    private final List<List<Integer>> array;

    public ArrayIndexComparator(List<List<Integer>> array){
        this.array = array;
    }

    public Integer[] createIndexArray(){
        Integer[] indexes = new Integer[array.size()];
        for (int i = 0; i < array.size(); i++){
            indexes[i] = i; // Autoboxing
        }
        return indexes;
    }

    @Override
    public int compare(Integer index1, Integer index2){
        return array.get(index1).size() - array.get(index2).size();
    }
}