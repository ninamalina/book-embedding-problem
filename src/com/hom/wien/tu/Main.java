package com.hom.wien.tu;

import com.hom.wien.tu.GeneticAlgorithm.CrossOver.ICrossOver;
import com.hom.wien.tu.GeneticAlgorithm.CrossOver.UniformCrossOver;
import com.hom.wien.tu.GeneticAlgorithm.GASolution;
import com.hom.wien.tu.GeneticAlgorithm.GeneticAlgorithm;
import com.hom.wien.tu.GeneticAlgorithm.Mutation.IMutation;
import com.hom.wien.tu.GeneticAlgorithm.Mutation.RandomMutation;
import com.hom.wien.tu.GeneticAlgorithm.Selection.ISelection;
import com.hom.wien.tu.GeneticAlgorithm.Selection.RandomSelection;
import com.hom.wien.tu.GeneticAlgorithm.Selection.RouletteWheelSelection;
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
		String filename = "instance-01";
		KPMPInstance instance = KPMPInstance.readInstance(filename + ".txt");
		Random randomNumberGenerator = new Random();

		Integer[] spineOrder = new Integer[instance.getNumVertices()];
		ArrayList<PageEntry> edgesPartition = new ArrayList<>();

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
		IMutation mutation = new RandomMutation(randomNumberGenerator, 0.02, 0.2);
		ICrossOver crossOver = new UniformCrossOver(randomNumberGenerator);
		ISelection selections = new RouletteWheelSelection(randomNumberGenerator);

		GeneticAlgorithm ga = new GeneticAlgorithm(30, true, 4, 100000, crossOver, mutation, selections, new GASolution(initialSolution));

		GASolution previousBest = null;
		while(ga.geneticAlgorithmIteration()){
			GASolution bestSolution = ga.getBestSolution();

			if(previousBest == null){
				previousBest = bestSolution;
			}else{
				if(bestSolution.getError() < previousBest.getError()){
					previousBest = bestSolution;

					double error = bestSolution.getError();
					System.out.println("Iteration: " + ga.currentIterationNumber() + ", error: " + error + ".");
				}
			}
		}





























    	/*String filename = "instance-15";
		Random randomNumberGenerator = new Random();

		KPMPInstance instance = KPMPInstance.readInstance(filename + ".txt");

		Integer[] spineOrder = new Integer[instance.getNumVertices()];
		ArrayList<PageEntry> edgesPartition = new ArrayList<>();

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
		initialSolution.calculateNumberOfCrossings();*/
//
		//INeighborhood[] neighborhoods = new INeighborhood[2];
		//neighborhoods[0] = new MoveEdgeNeighborhood();
		//neighborhoods[1] = new SwapVertices();
////
		//Search search = new LocalSearch(initialSolution, stepFunction, neighborhood);
		//Search search = new VariableNeighborhoodDescent(neighborhoods, initialSolution);
		//Search search = new GeneralVariableNeighborhoodSearch(firstNeighborhoods, secondNeighborhoods, initialSolution);
		//search.search();


		//initialSolution.calculateNumberOfCrossingsForPages();
		//writeSolutionToFile(initialSolution, filename + "_" +".txt");

		//System.out.println(filename + "_" + " crossings: " + initialSolution.calculateCrossingsFromMap());
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
