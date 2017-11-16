package com.hom.wien.tu.Neighborhood;

import com.hom.wien.tu.Utilities.KPMPSolution;

/**
 * Created by davorsafranko on 11/10/17.
 */
public interface INeighborhood {

    int randomNeighbor(KPMPSolution solution);

    int firstNeighbor(KPMPSolution solution);

    int bestNeighbor(KPMPSolution solution);
}
