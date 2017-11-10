package com.hom.wien.tu.Neighborhood;

import com.hom.wien.tu.Utilities.KPMPSolution;

import java.util.List;

/**
 * Created by davorsafranko on 11/10/17.
 */
public class SwapVertices extends Neighborhood {

    public SwapVertices(List<List<Integer>> adjacencyList, boolean[][] adjacencyMatrix) {
        super(adjacencyList, adjacencyMatrix);
    }

    @Override
    protected KPMPSolution[] neighborhoodSolutions(KPMPSolution solution) {
        return null;
    }
}
