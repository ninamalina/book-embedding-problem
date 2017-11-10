package com.hom.wien.tu.StepFunction;

import com.hom.wien.tu.Neighborhood.Neighborhood;
import com.hom.wien.tu.Utilities.KPMPSolution;

/**
 * Created by davorsafranko on 11/9/17.
 */
public class RandomImprovement implements IStepFunction {

    private Neighborhood neighborhood;

    public RandomImprovement(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    @Override
    public KPMPSolution nextNeighbor(KPMPSolution currentSolution) {
        return currentSolution;
    }
}
