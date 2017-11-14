package com.hom.wien.tu.Neighborhood;

import com.hom.wien.tu.Utilities.KPMPSolution;
import com.hom.wien.tu.Utilities.PageEntry;

import java.util.Random;

/**
 * Created by davorsafranko on 11/10/17.
 */
public class MoveEdgeNeighborhood implements INeighborhood {

    private Random randomNumberGenerator;

    public MoveEdgeNeighborhood() {
        this.randomNumberGenerator = new Random();
    }

    @Override
    public KPMPSolution randomNeighbor(KPMPSolution solution) {
        PageEntry[] newEdgePartition = solution.getEdgePartition().clone();
        int randomEdge = randomNumberGenerator.nextInt(newEdgePartition.length);
        PageEntry selectedPageEntry = newEdgePartition[randomEdge];

        int randomPage;
        do {
            randomPage = randomNumberGenerator.nextInt(solution.numberOfPages());
        } while(randomPage == selectedPageEntry.page);


        PageEntry newPageEntry = new PageEntry(selectedPageEntry.a, selectedPageEntry.b, randomPage);
        newEdgePartition[randomEdge] = newPageEntry;

        KPMPSolution randomSolution = new KPMPSolution(solution.getSpineOrder(), newEdgePartition, solution.numberOfPages());
        return randomSolution;
    }

    @Override
    public KPMPSolution firstNeighbor(KPMPSolution solution) {
        PageEntry[] newEdgePartition = solution.getEdgePartition().clone();

        for(int edgeIndex = 0; edgeIndex < newEdgePartition.length; edgeIndex++) {
            PageEntry thisPageEntry = newEdgePartition[edgeIndex];
            for(int i = 0; i < solution.numberOfPages(); i++) {

                if(i != thisPageEntry.page) {
                    PageEntry newPageEntry = new PageEntry(thisPageEntry.a, thisPageEntry.b, i);
                    newEdgePartition[edgeIndex] = newPageEntry;

                    KPMPSolution neighborSolution = new KPMPSolution(solution.getSpineOrder(), newEdgePartition, solution.numberOfPages());
                    if (neighborSolution.numberOfCrossings() < solution.numberOfCrossings()) {
                        return neighborSolution;
                    }

                    newEdgePartition[edgeIndex] = thisPageEntry;
                }
            }
        }

        return solution;
    }

    @Override
    public KPMPSolution bestNeighbor(KPMPSolution solution) {
        PageEntry[] newEdgePartition = solution.getEdgePartition().clone();
        int minimumCrossingsValue = Integer.MAX_VALUE;
        int minEdgeIndex = 0;
        int pageNumber = 0;

        for(int edgeIndex = 0; edgeIndex < newEdgePartition.length; edgeIndex++) {
            PageEntry thisPageEntry = newEdgePartition[edgeIndex];
            for(int i = 0; i < solution.numberOfPages(); i++) {

                if(i != thisPageEntry.page) {
                    PageEntry newPageEntry = new PageEntry(thisPageEntry.a, thisPageEntry.b, i);
                    newEdgePartition[edgeIndex] = newPageEntry;

                    KPMPSolution neighborSolution = new KPMPSolution(solution.getSpineOrder(), newEdgePartition, solution.numberOfPages());
                    if (neighborSolution.numberOfCrossings() < minimumCrossingsValue) {
                        minimumCrossingsValue = neighborSolution.numberOfCrossings();
                        minEdgeIndex = edgeIndex;
                        pageNumber = i;
                    }

                    newEdgePartition[edgeIndex] = thisPageEntry;
                }
            }
        }

        PageEntry newPageEntry = new PageEntry(newEdgePartition[minEdgeIndex].a, newEdgePartition[minEdgeIndex].b, pageNumber);
        newEdgePartition[minEdgeIndex] = newPageEntry;

        KPMPSolution bestSolution = new KPMPSolution(solution.getSpineOrder(), newEdgePartition, solution.numberOfPages());

        return bestSolution;
    }
}