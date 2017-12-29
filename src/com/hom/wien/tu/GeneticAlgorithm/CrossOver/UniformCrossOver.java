package com.hom.wien.tu.GeneticAlgorithm.CrossOver;

import com.hom.wien.tu.GeneticAlgorithm.GASolution;
import com.hom.wien.tu.Utilities.PageEntry;
import sun.jvm.hotspot.debugger.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by davorsafranko on 12/29/17.
 */
public class UniformCrossOver implements ICrossOver {

    private Random randomNumberGenerator;

    public UniformCrossOver(Random randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    @Override
    public GASolution crossOver(GASolution firstParent, GASolution secondParent) {
        Integer[] spineOrder;

        if(randomNumberGenerator.nextDouble() < 0.5) {
            spineOrder = firstParent.getSpineOrder().clone();
        } else {
            spineOrder = secondParent.getSpineOrder().clone();
        }

        List<PageEntry> firstParentEdgePartition = firstParent.getEdgePartition();
        List<PageEntry> secondParentEdgePartition = secondParent.getEdgePartition();
        int edgePartitionSize = firstParent.getEdgePartition().size();
        List<PageEntry> edgePartition = new ArrayList<>(edgePartitionSize);

        for(int i = 0; i < edgePartitionSize; i++) {
            if(randomNumberGenerator.nextDouble() < 0.5) {
                edgePartition.add(firstParentEdgePartition.get(i));
            } else {
                edgePartition.add(secondParentEdgePartition.get(i));
            }
        }

        return new GASolution(spineOrder, edgePartition, firstParent.numberOfPages());
    }
}
