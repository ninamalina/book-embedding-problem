package com.hom.wien.tu.Search;

import com.hom.wien.tu.Neighborhood.INeighborhood;
import com.hom.wien.tu.StepFunction.IStepFunction;
import com.hom.wien.tu.Utilities.KPMPSolution;

/**
 * Created by davorsafranko on 11/9/17.
 */
public class LocalSearch extends Search {

    private IStepFunction stepFunction;
    private INeighborhood neighborhood;
    private int currentNumberOfCrossings;

    public LocalSearch(KPMPSolution initialSolution, IStepFunction stepFunction, INeighborhood neighborhood) {
        super(initialSolution);
        this.stepFunction = stepFunction;
        this.neighborhood = neighborhood;

        this.currentNumberOfCrossings = initialSolution.calculateCrossingsFromMap();
    }

    @Override
    protected void prepareSearch() {

    }

    @Override
    protected void findSolution() {
        /*KPMPSolution newSolution = stepFunction.nextNeighbor(currentSolution, neighborhood);
        if (newSolution.numberOfCrossings() < currentSolution.numberOfCrossings()) {
            currentSolution = newSolution;
            System.out.println("Current number of crossings: " + currentSolution.numberOfCrossings() + "\n");
        }*/

        stepFunction.nextNeighbor(currentSolution, neighborhood);
        int newNumberOfCrossings = currentSolution.calculateCrossingsFromMap();

        if(newNumberOfCrossings < currentNumberOfCrossings) {
            currentNumberOfCrossings = newNumberOfCrossings;
            System.out.println("Current number of crossings: " + newNumberOfCrossings + "\n");
            //System.out.println("Current number of crossings2: " + currentSolution.numberOfCrossings() + "\n");
        }

    }

    @Override
    public boolean shouldContinue() {
        long currentTime = System.currentTimeMillis();

        return (currentTime - startTime) < 900000;
    }
}
