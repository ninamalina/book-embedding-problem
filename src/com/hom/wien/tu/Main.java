package com.hom.wien.tu;

import com.hom.wien.tu.Neighborhood.INeighborhood;
import com.hom.wien.tu.Neighborhood.MoveEdgeNeighborhood;
import com.hom.wien.tu.Neighborhood.SwapVertices;
import com.hom.wien.tu.Search.GeneralVariableNeighborhoodSearch;
import com.hom.wien.tu.Search.LocalSearch;
import com.hom.wien.tu.Search.Search;
import com.hom.wien.tu.Search.VariableNeighborhoodDescent;
import com.hom.wien.tu.StepFunction.BestImprovement;
import com.hom.wien.tu.StepFunction.FirstImprovement;
import com.hom.wien.tu.StepFunction.IStepFunction;
import com.hom.wien.tu.StepFunction.RandomImprovement;
import com.hom.wien.tu.Utilities.KPMPInstance;
import com.hom.wien.tu.Utilities.KPMPSolution;
import com.hom.wien.tu.Utilities.PageEntry;
import com.hom.wien.tu.Utilities.Pair;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        KPMPInstance instance = KPMPInstance.readInstance("instance-01.txt");

        INeighborhood neighborhood = new MoveEdgeNeighborhood();
        IStepFunction stepFunction = new BestImprovement();

        Integer[] spineOrder = new Integer[instance.getNumVertices()];
        ArrayList<PageEntry> edgesPartition = new ArrayList<>();

        Random randomNumberGenerator = new Random();
        for(int i = 0; i < instance.getAdjacencyList().size(); i++) {
            spineOrder[i] = i;
            for(int j = 0; j < instance.getAdjacencyList().get(i).size(); j++) {
                edgesPartition.add(new PageEntry(i, instance.getAdjacencyList().get(i).get(j), randomNumberGenerator.nextInt(instance.getNumberOfPages())));
            }
        }

        KPMPSolution initialSolution = new KPMPSolution(spineOrder, edgesPartition.stream().toArray(PageEntry[]::new), instance.getNumberOfPages());
        //ISearch search = new LocalSearch(initialSolution, stepFunction, neighborhood);

        INeighborhood[] neighborhoods = new INeighborhood[2];
        neighborhoods[0] = new SwapVertices();
        neighborhoods[1] = new MoveEdgeNeighborhood();

        //Search search = new LocalSearch(initialSolution, stepFunction, neighborhood);
        //Search search = new VariableNeighborhoodDescent(neighborhoods, initialSolution);

        INeighborhood[] firstNeighborhoods = new INeighborhood[2];
        firstNeighborhoods[0] = new SwapVertices();
        firstNeighborhoods[1] = new MoveEdgeNeighborhood();

        INeighborhood[] secondNeighborhoods = new INeighborhood[2];
        secondNeighborhoods[0] = new SwapVertices();
        secondNeighborhoods[1] = new MoveEdgeNeighborhood();

        Search search = new GeneralVariableNeighborhoodSearch(firstNeighborhoods, secondNeighborhoods, initialSolution);
        search.search();
    }
}
