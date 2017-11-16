package com.hom.wien.tu.StepFunction;

import com.hom.wien.tu.Neighborhood.INeighborhood;
import com.hom.wien.tu.Utilities.KPMPSolution;

/**
 * Created by davorsafranko on 11/9/17.
 */
public class BestImprovement implements IStepFunction {

    @Override
    public int nextNeighbor(KPMPSolution currentSolution, INeighborhood neighborhood) {
        return neighborhood.bestNeighbor(currentSolution);
    }
}
