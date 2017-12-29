package com.hom.wien.tu.GeneticAlgorithm;

import com.hom.wien.tu.GeneticAlgorithm.CrossOver.ICrossOver;
import com.hom.wien.tu.GeneticAlgorithm.Mutation.IMutation;
import com.hom.wien.tu.GeneticAlgorithm.Selection.ISelection;
import com.hom.wien.tu.Utilities.PageEntry;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by davorsafranko on 12/28/17.
 */
public class GeneticAlgorithm {

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

    public GeneticAlgorithm(int populationSize, boolean elitism, int keep, int nIterations, ICrossOver crossOver, IMutation mutation, ISelection selection, GASolution initialGraph) {
        this.populationSize = populationSize;
        this.elitism = elitism;
        this.keep = keep;
        this.nIterations = nIterations;
        this.crossOver = crossOver;
        this.mutation = mutation;
        this.selection = selection;
        this.initialGraph = initialGraph;

        this.iterationCounter = 0;
        this.randomNumberGenerator = new Random();
        this.population = new ArrayList<>(populationSize);

        createFirstGeneration();
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
            population.add(solution);
        }

        this.population.sort(Comparator.comparingInt(GASolution::getError));
    }
}
