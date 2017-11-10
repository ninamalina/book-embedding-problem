package com.hom.wien.tu.Search;

import com.hom.wien.tu.StepFunction.IStepFunction;
import com.hom.wien.tu.Utilities.KPMPSolution;

/**
 * Created by davorsafranko on 11/9/17.
 */
public class LocalSearch implements ISearch {

    private int numberOfPages;
    private KPMPSolution initialSolution;
    private IStepFunction stepFunction;
    private long startTime;

    public LocalSearch(KPMPSolution initialSolution, IStepFunction stepFunction, int numberOfPages) {
        this.initialSolution = initialSolution;
        this.stepFunction = stepFunction;
        this.numberOfPages = numberOfPages;
    }

    @Override
    public KPMPSolution search() {
        this.startTime = System.currentTimeMillis();

        KPMPSolution currentSolution = initialSolution;
        while(shouldContinue()) {
            KPMPSolution newSolution = stepFunction.nextNeighbor(currentSolution);
            if (newSolution.numberOfCrossings() < currentSolution.numberOfCrossings()) {
                currentSolution = newSolution;
            }
        }

        return currentSolution;
    }

    @Override
    public boolean shouldContinue() {
        long currentTime = System.currentTimeMillis();

        return (currentTime - startTime) < 900000;
    }
}
