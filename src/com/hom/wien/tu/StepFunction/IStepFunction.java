package com.hom.wien.tu.StepFunction;

import com.hom.wien.tu.Utilities.KPMPSolution;

/**
 * Created by davorsafranko on 11/9/17.
 */
public interface IStepFunction {

    KPMPSolution nextNeighbor(KPMPSolution currentSolution);
}
