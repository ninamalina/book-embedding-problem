package com.hom.wien.tu.StepFunction;

import com.hom.wien.tu.Neighborhood.Neighborhood;
import com.hom.wien.tu.Utilities.KPMPSolution;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by davorsafranko on 11/9/17.
 */
public class FirstImprovement implements IStepFunction {

    private Neighborhood neighborhood;

    public FirstImprovement(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    @Override
    public KPMPSolution nextNeighbor(KPMPSolution currentSolution) {
        return null;
    }
}
