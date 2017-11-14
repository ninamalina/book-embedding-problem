package com.hom.wien.tu.Neighborhood;

import com.hom.wien.tu.Utilities.KPMPSolution;

/**
 * Created by davorsafranko on 11/10/17.
 */
public interface INeighborhood {

    void randomNeighbor(KPMPSolution solution);

    void firstNeighbor(KPMPSolution solution);

    void bestNeighbor(KPMPSolution solution);
}
