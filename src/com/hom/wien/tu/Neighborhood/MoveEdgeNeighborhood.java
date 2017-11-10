package com.hom.wien.tu.Neighborhood;

import com.hom.wien.tu.Utilities.KPMPSolution;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by davorsafranko on 11/10/17.
 */
public class MoveEdgeNeighborhood extends Neighborhood {

    public MoveEdgeNeighborhood(List<List<Integer>>adjacencyList, boolean[][] adjacencyMatrix) {
        super(adjacencyList, adjacencyMatrix);
    }

    @Override
    protected KPMPSolution[] neighborhoodSolutions(KPMPSolution solution) {
        throw new NotImplementedException();
    }
}
