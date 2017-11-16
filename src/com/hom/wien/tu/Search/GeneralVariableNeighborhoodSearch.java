package com.hom.wien.tu.Search;

import com.hom.wien.tu.Neighborhood.INeighborhood;
import com.hom.wien.tu.StepFunction.IStepFunction;
import com.hom.wien.tu.StepFunction.RandomImprovement;
import com.hom.wien.tu.Utilities.KPMPSolution;

/**
 * Created by davorsafranko on 11/13/17.
 */
public class GeneralVariableNeighborhoodSearch extends Search {

    private INeighborhood[] firstNeighborhoods;
    private INeighborhood[] secondNeighborhoods;
    private IStepFunction stepFunction;
    private int index = 0;

    public GeneralVariableNeighborhoodSearch(INeighborhood[] firstNeighborhoods, INeighborhood[] secondNeighborhoods, KPMPSolution initialSolution) {
        super(initialSolution);

        this.firstNeighborhoods = firstNeighborhoods;
        this.secondNeighborhoods = secondNeighborhoods;
        this.stepFunction = new RandomImprovement();
    }

    @Override
    protected void prepareSearch() {
        index = 0;
    }

    @Override
    protected boolean findSolution() {
        while(index < firstNeighborhoods.length) {
            stepFunction.nextNeighbor(currentSolution, firstNeighborhoods[index]);

            VariableNeighborhoodDescent vnd = new VariableNeighborhoodDescent(secondNeighborhoods, currentSolution);
            vnd.findSolution();
            KPMPSolution currentNeighborhoodBestSolution = vnd.getBestSolution();

            if (currentNeighborhoodBestSolution.numberOfCrossings() < currentSolution.numberOfCrossings()) {
                currentSolution = currentNeighborhoodBestSolution;
                index = 0;

                return true;
            } else {
                index++;
            }
        }

        return false;
    }

    @Override
    protected boolean shouldContinue() {
        long currentTime = System.currentTimeMillis();

        return (currentTime - startTime) < 900000;
    }
}
