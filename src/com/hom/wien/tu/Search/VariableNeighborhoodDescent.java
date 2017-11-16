package com.hom.wien.tu.Search;

import com.hom.wien.tu.Neighborhood.INeighborhood;
import com.hom.wien.tu.StepFunction.BestImprovement;
import com.hom.wien.tu.StepFunction.IStepFunction;
import com.hom.wien.tu.Utilities.KPMPSolution;


/**
 * Created by davorsafranko on 11/13/17.
 */
public class VariableNeighborhoodDescent extends Search {

    private INeighborhood[] neighborhoods;
    private IStepFunction stepFunction;
    private int index = 0;
    private int currentNumberOfCrossings;

    public VariableNeighborhoodDescent(INeighborhood[] neighborhoods, KPMPSolution initialSolution) {
        super(initialSolution);
        this.neighborhoods = neighborhoods;
        this.stepFunction = new BestImprovement();

        this.currentNumberOfCrossings = initialSolution.calculateCrossingsFromMap();
    }

    @Override
    protected void prepareSearch() {
        index = 0;
    }

    @Override
    protected boolean findSolution() {
        while(index < neighborhoods.length) {
            int newNumberOfCrossings = stepFunction.nextNeighbor(currentSolution, neighborhoods[index]);
            if (newNumberOfCrossings < currentNumberOfCrossings) {
                currentNumberOfCrossings = newNumberOfCrossings;
                index = 0;

                return true;
            } else {
                index++;
            }
        }

        return false;
    }

    @Override
    public boolean shouldContinue() {
        long currentTime = System.currentTimeMillis();

        return (currentTime - startTime) < 900000;
    }
}
