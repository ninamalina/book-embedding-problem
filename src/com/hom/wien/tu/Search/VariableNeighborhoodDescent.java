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

    public VariableNeighborhoodDescent(INeighborhood[] neighborhoods, KPMPSolution initialSolution) {
        super(initialSolution);
        this.neighborhoods = neighborhoods;
        this.stepFunction = new BestImprovement();

        currentSolution = initialSolution;
    }

    @Override
    protected void prepareSearch() {
        index = 0;
    }

    @Override
    protected void findSolution() {
        while(index < neighborhoods.length) {
            KPMPSolution currentNeighborhoodBestSolution = stepFunction.nextNeighbor(currentSolution, neighborhoods[index]);
            if (currentNeighborhoodBestSolution.numberOfCrossings() < currentSolution.numberOfCrossings()) {
                currentSolution = currentNeighborhoodBestSolution;
                index = 0;
                //System.out.println("Current number of crossings: " + currentSolution.numberOfCrossings() + "\n");
            } else {
                index++;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        long currentTime = System.currentTimeMillis();

        return (currentTime - startTime) < 900000;
    }
}
