package com.hom.wien.tu;

import com.hom.wien.tu.Neighborhood.MoveEdgeNeighborhood;
import com.hom.wien.tu.Neighborhood.Neighborhood;
import com.hom.wien.tu.Search.ISearch;
import com.hom.wien.tu.Search.LocalSearch;
import com.hom.wien.tu.StepFunction.IStepFunction;
import com.hom.wien.tu.StepFunction.RandomImprovement;
import com.hom.wien.tu.Utilities.KPMPInstance;
import com.hom.wien.tu.Utilities.KPMPSolution;
import com.hom.wien.tu.Utilities.PageEntry;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        KPMPInstance instance = KPMPInstance.readInstance("instance-01.txt");

        Neighborhood neighborhood = new MoveEdgeNeighborhood(instance.getAdjacencyList(), instance.getAdjacencyMatrix());
        IStepFunction stepFunction = new RandomImprovement(neighborhood);

        Integer[] spineOrder = new Integer[instance.getNumVertices()];
        ArrayList<PageEntry> edgesPartition = new ArrayList<>();

        for(int i = 0; i < instance.getAdjacencyList().size(); i++) {
            spineOrder[i] = i;
            for(int j = 0; j < instance.getAdjacencyList().get(i).size(); j++) {
                edgesPartition.add(new PageEntry(i, instance.getAdjacencyList().get(i).get(j), 0));
            }
        }

        KPMPSolution initialSolution = new KPMPSolution(spineOrder, edgesPartition.stream().toArray(PageEntry[]::new), instance.getNumberOfPages());
        ISearch search = new LocalSearch(initialSolution, stepFunction, instance.getNumberOfPages());

        KPMPSolution bestSolution = search.search();
        System.out.println(bestSolution.getSpineOrder());
        System.out.println(bestSolution.getEdgePartition());
    }
}
