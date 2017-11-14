package com.hom.wien.tu.StepFunction;

import com.hom.wien.tu.Neighborhood.INeighborhood;
import com.hom.wien.tu.Utilities.KPMPSolution;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by davorsafranko on 11/9/17.
 */
public class FirstImprovement implements IStepFunction {

    @Override
    public KPMPSolution nextNeighbor(KPMPSolution currentSolution, INeighborhood neighborhood) {
        neighborhood.firstNeighbor(currentSolution);
        return currentSolution;
    }
}
