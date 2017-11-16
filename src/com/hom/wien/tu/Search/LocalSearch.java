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
    protected boolean findSolution() {
        int newNumberOfCrossings = stepFunction.nextNeighbor(currentSolution, neighborhood);

        if (newNumberOfCrossings < currentNumberOfCrossings) {
            currentNumberOfCrossings = newNumberOfCrossings;
            return true;
        }

        return false;
    }

    @Override
    public boolean shouldContinue() {
        long currentTime = System.currentTimeMillis();

        return (currentTime - startTime) < 900000;
    }
}
