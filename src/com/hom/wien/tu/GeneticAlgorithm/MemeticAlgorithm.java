package com.hom.wien.tu.GeneticAlgorithm;

import com.hom.wien.tu.GeneticAlgorithm.CrossOver.ICrossOver;
import com.hom.wien.tu.GeneticAlgorithm.Mutation.IMutation;
import com.hom.wien.tu.GeneticAlgorithm.Selection.ISelection;
import com.hom.wien.tu.Neighborhood.INeighborhood;
import com.hom.wien.tu.Search.LocalSearch;
import com.hom.wien.tu.Search.Search;
import com.hom.wien.tu.StepFunction.IStepFunction;
import com.hom.wien.tu.Utilities.PageEntry;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by davorsafranko on 12/29/17.
 */
public class MemeticAlgorithm {

    private int populationSize;
    private boolean elitism;
    private int keep;
    private Random randomNumberGenerator;
    private int nIterations;
    private ICrossOver crossOver;
    private IMutation mutation;
    private int iterationCounter;
    private List<GASolution> population;
    private ISelection selection;
    private GASolution initialGraph;
    private IStepFunction stepFunction;
    private INeighborhood neighborhood;

    public MemeticAlgorithm(int populationSize, boolean elitism, int keep, int nIterations, ICrossOver crossOver, IMutation mutation, ISelection selection, GASolution initialGraph, IStepFunction stepFunction, INeighborhood neighborhood) {
        this.populationSize = populationSize;
        this.elitism = elitism;
        this.keep = keep;
        this.nIterations = nIterations;
        this.crossOver = crossOver;
        this.mutation = mutation;
        this.selection = selection;
        this.initialGraph = initialGraph;
        this.stepFunction = stepFunction;
        this.neighborhood = neighborhood;

        this.iterationCounter = 0;
        this.randomNumberGenerator = new Random();
        this.population = new ArrayList<>(populationSize);

        createFirstGeneration();
        improveFirstGeneration();
    }

    private void createFirstGeneration() {
        for(int i = 0; i < populationSize; i++) {
            List<Integer> spineOrderList = Arrays.asList(initialGraph.getSpineOrder());
            Collections.shuffle(spineOrderList);
            Integer[] spineOrder = spineOrderList.toArray(new Integer[0]);

            List<PageEntry> pages = new ArrayList<>(initialGraph.getEdgePartition());
            for(PageEntry pageEntry : pages) {
                pageEntry.page = randomNumberGenerator.nextInt(initialGraph.numberOfPages());
            }

            GASolution solution = new GASolution(spineOrder, pages, initialGraph.numberOfPages());
            solution.calculateNumberOfCrossingsForPages();
            population.add(solution);
        }
    }

    public boolean geneticAlgorithmIteration() {
        List<GASolution> newPopulation = new ArrayList<>(populationSize);

        if(this.elitism){
            List<GASolution> bestN = getNBestUnits(this.keep);
            newPopulation.addAll(bestN.stream().collect(Collectors.toList()));
        }

        while(newPopulation.size() < this.populationSize) {
            List<GASolution> parents = selection.pickParents(this.population);
            GASolution child = crossOver.crossOver(parents.get(0), parents.get(1));
            mutation.mutate(child);
            newPopulation.add(child);
        }

        newPopulation.sort(Comparator.comparingDouble(GASolution::getError));
        this.population = newPopulation;
        iterationCounter += 1;

        return this.iterationCounter != this.nIterations;
    }

    public GASolution getBestSolution() {
        return population.get(0);
    }

    public int currentIterationNumber() {
        return iterationCounter;
    }

    private List<GASolution> getNBestUnits(int n){
        List<GASolution> bestN = new ArrayList<>(n);

        for(int i = 0; i < n; i++){
            bestN.add(new GASolution(population.get(i)));
        }

        return bestN;
    }

    private void improveFirstGeneration() {

        for(int i = 0; i < 4; i++) {
            Search localSearch = new LocalSearch(population.get(i), stepFunction, neighborhood);
            localSearch.search();
            population.get(i).setNumberOfCrossings(population.get(i).calculateCrossingsFromMap());
        }

        this.population.sort(Comparator.comparingInt(GASolution::getError));
    }

    public void improveBestSolution() {
        Search localSearch = new LocalSearch(population.get(0), stepFunction, neighborhood);
        localSearch.search();

        population.get(0).setNumberOfCrossings(population.get(0).calculateCrossingsFromMap());
    }

}
