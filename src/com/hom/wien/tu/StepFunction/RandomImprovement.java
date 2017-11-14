package com.hom.wien.tu.StepFunction;

import com.hom.wien.tu.Neighborhood.INeighborhood;
import com.hom.wien.tu.Utilities.KPMPSolution;

/**
 * Created by davorsafranko on 11/9/17.
 */
public class RandomImprovement implements IStepFunction {

    @Override
    public KPMPSolution nextNeighbor(KPMPSolution currentSolution, INeighborhood neighborhood) {
        neighborhood.randomNeighbor(currentSolution);
        return currentSolution;
    }
}
