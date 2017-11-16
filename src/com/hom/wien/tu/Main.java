package com.hom.wien.tu;

import com.hom.wien.tu.Neighborhood.INeighborhood;
import com.hom.wien.tu.Construction.DeterministicConstruction;
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
import com.hom.wien.tu.Utilities.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
		int minIndex = 0;
		int minValue = Integer.MAX_VALUE;

		for(int t = 0; t < 100; t++) {
			KPMPInstance instance = KPMPInstance.readInstance("instance-09.txt");

			INeighborhood neighborhood = new MoveEdgeNeighborhood();
			IStepFunction stepFunction = new RandomImprovement();

			Integer[] spineOrder = new Integer[instance.getNumVertices()];
			ArrayList<PageEntry> edgesPartition = new ArrayList<>();

			Random randomNumberGenerator = new Random();
			for (int i = 0; i < instance.getAdjacencyList().size(); i++) {
				spineOrder[i] = i;
				for (int j = 0; j < instance.getAdjacencyList().get(i).size(); j++) {
					int neighbour = instance.getAdjacencyList().get(i).get(j);
					if (i < neighbour){
						edgesPartition.add(new PageEntry(i, neighbour, randomNumberGenerator.nextInt(instance.getNumberOfPages())));
					}
				}
			}

			KPMPSolution initialSolution = new KPMPSolution(spineOrder, edgesPartition, instance.getNumberOfPages());
			initialSolution.calculateNumberOfCrossingsForPages();
			initialSolution.calculateNumberOfCrossings();
//
			INeighborhood[] neighborhoods = new INeighborhood[2];
			neighborhoods[0] = new SwapVertices();
			neighborhoods[1] = new MoveEdgeNeighborhood();
////
			Search search = new LocalSearch(initialSolution, stepFunction, neighborhood);
			//Search search = new VariableNeighborhoodDescent(neighborhoods, initialSolution);
//
//		INeighborhood[] firstNeighborhoods = new INeighborhood[2];
//		firstNeighborhoods[0] = new SwapVertices();
//		firstNeighborhoods[1] = new MoveEdgeNeighborhood();
//
//		INeighborhood[] secondNeighborhoods = new INeighborhood[2];
//		secondNeighborhoods[0] = new SwapVertices();
//		secondNeighborhoods[1] = new MoveEdgeNeighborhood();
//
			//Search search = new GeneralVariableNeighborhoodSearch(firstNeighborhoods, secondNeighborhoods, initialSolution);
			search.search();


			if(initialSolution.calculateCrossingsFromMap() < minValue) {
				minIndex = t;
				minValue = initialSolution.calculateCrossingsFromMap();
				writeSolutionToFile(initialSolution, "instance06" + t +".txt");
			}

			System.out.println("Total number of crossings: " + initialSolution.calculateCrossingsFromMap());
		}

		System.out.println("Min value = " + minValue + "for index " + minIndex);
//	}
///*=======
//import java.lang.System;
//public class Main {
//
//    public static void main(String[] args) throws FileNotFoundException {
//    	for (int i = 1; i<16; i++ ){
//	        KPMPInstance instance = KPMPInstance.readInstance(String.format("instances/instance-%02d.txt", i));
//	        System.out.println("Instance " + i);
//	        System.out.println("Deterministic: ");
//	        long start = System.currentTimeMillis();
//	        KPMPSolution solution = computeDeterministic(instance);
//	        long end = System.currentTimeMillis();
//	        System.out.println("Execution time - " + (end - start)/1000.0);
//	        System.out.println("Number of crossings - " + solution.numberOfCrossings());
//	        System.out.println("Randomized: ");
//	        start = System.currentTimeMillis();
//	        KPMPSolution solutionR = computeRandomized(instance);
//	        end = System.currentTimeMillis();
//	        System.out.println("Execution time - " + (end - start)/1000.0);
//	        System.out.println("Number of crossings - " + solutionR.numberOfCrossings());    	}

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

    public static void writeSolutionToFile(KPMPSolution solution, String fileName) throws IOException {
		KPMPSolutionWriter writer = new KPMPSolutionWriter(solution.numberOfPages());
		writer.setSpineOrder(Arrays.asList(solution.getSpineOrder()));

		for(PageEntry pageEntry: solution.getEdgePartition()) {
			writer.addEdgeOnPage(pageEntry.a, pageEntry.b, pageEntry.page);
		}

		writer.write(fileName);
	}
}
