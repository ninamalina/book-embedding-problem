package com.hom.wien.tu.Neighborhood;

import com.hom.wien.tu.Utilities.KPMPSolution;

/**
 * Created by davorsafranko on 11/10/17.
 */
public interface INeighborhood {

    KPMPSolution randomNeighbor(KPMPSolution solution);

    KPMPSolution firstNeighbor(KPMPSolution solution);

    KPMPSolution bestNeighbor(KPMPSolution solution);
}
