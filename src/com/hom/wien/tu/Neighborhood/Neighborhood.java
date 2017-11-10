package com.hom.wien.tu.Neighborhood;

import com.hom.wien.tu.Utilities.KPMPSolution;

import java.util.List;

/**
 * Created by davorsafranko on 11/10/17.
 */
public abstract class Neighborhood {

    private List<List<Integer>> adjacencyList;
    private boolean[][] adjacencyMatrix;

    public Neighborhood(List<List<Integer>>adjacencyList, boolean[][] adjacencyMatrix) {
        this.adjacencyList = adjacencyList;
        this.adjacencyMatrix = adjacencyMatrix;
    }

    protected abstract KPMPSolution[] neighborhoodSolutions(KPMPSolution solution);
}
