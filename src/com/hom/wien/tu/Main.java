package com.hom.wien.tu;

import com.hom.wien.tu.Construction.DeterministicConstruction;
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
//    	for (int i = 1; i<10; i++ ){
//	        KPMPInstance instance = KPMPInstance.readInstance("instances/instance-0"+i+".txt");
//	        KPMPSolution solution = computeDeterministic(instance);
//	        KPMPSolution solutionR = computeRandomized(instance);
//	        System.out.println(solution.numberOfCrossings() + " " + solutionR.numberOfCrossings());
//    	}
    	for (int i = 10; i<16; i++ ){
	        KPMPInstance instance = KPMPInstance.readInstance("instances/instance-"+i+".txt");
	        KPMPSolution solution = computeDeterministic(instance);
	        KPMPSolution solutionR = computeRandomized(instance);
	        System.out.println(solution.numberOfCrossings() + " " + solutionR.numberOfCrossings());
    	}

//        Neighborhood neighborhood = new MoveEdgeNeighborhood(instance.getAdjacencyList(), instance.getAdjacencyMatrix());
//        IStepFunction stepFunction = new RandomImprovement(neighborhood);
//
//        Integer[] spineOrder = new Integer[instance.getNumVertices()];
//        ArrayList<PageEntry> edgesPartition = new ArrayList<>();
//
//        for(int i = 0; i < instance.getAdjacencyList().size(); i++) {
//            spineOrder[i] = i;
//            for(int j = 0; j < instance.getAdjacencyList().get(i).size(); j++) {
//                edgesPartition.add(new PageEntry(i, instance.getAdjacencyList().get(i).get(j), 0));
//            }
//        }
//
//        KPMPSolution initialSolution = new KPMPSolution(spineOrder, edgesPartition.stream().toArray(PageEntry[]::new), instance.getNumberOfPages());
//        ISearch search = new LocalSearch(initialSolution, stepFunction, instance.getNumberOfPages());
//
//        KPMPSolution bestSolution = search.search();
//        System.out.println(bestSolution.getSpineOrder());
//        System.out.println(bestSolution.getEdgePartition());
    }
    
    public static KPMPSolution computeDeterministic(KPMPInstance instance){
        DeterministicConstruction construction = new DeterministicConstruction();
        KPMPSolution solution = construction.buildSolution(instance, false);
        return solution;
    }
    
    public static KPMPSolution computeRandomized(KPMPInstance instance){
        DeterministicConstruction construction = new DeterministicConstruction();
        KPMPSolution solution = construction.buildSolution(instance, true);
        return solution;
    }
}
