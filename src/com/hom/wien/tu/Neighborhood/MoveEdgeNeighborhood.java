package com.hom.wien.tu.Neighborhood;

import com.hom.wien.tu.Utilities.KPMPSolution;
import com.hom.wien.tu.Utilities.PageEntry;

import java.util.List;
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
    public void randomNeighbor(KPMPSolution solution) {
        List<PageEntry> edgePartition = solution.getEdgePartition();
        int randomEdgeIndex = randomNumberGenerator.nextInt(edgePartition.size());
        PageEntry selectedPageEntry = edgePartition.remove(randomEdgeIndex);

        int randomPage;
        do {
            randomPage = randomNumberGenerator.nextInt(solution.numberOfPages());
        } while(randomPage == selectedPageEntry.page);

        PageEntry newPageEntry = new PageEntry(selectedPageEntry.a, selectedPageEntry.b, randomPage);
        int numberOfCrossingsOnFirstPage = solution.numberOfCrossingsOnPageForEdge(selectedPageEntry);
        int numberOfCrossingsOnSecondPage = solution.numberOfCrossingsOnPageForEdge(newPageEntry);

        if( numberOfCrossingsOnSecondPage < numberOfCrossingsOnFirstPage) {
        	solution.moveEdgeFromPageToPage(selectedPageEntry, newPageEntry, numberOfCrossingsOnFirstPage, numberOfCrossingsOnSecondPage);
          } else {
        	edgePartition.add(selectedPageEntry);
        }
    }

    @Override
    public void firstNeighbor(KPMPSolution solution) {
    	 List<PageEntry> edgePartition = solution.getEdgePartition();
         int n = edgePartition.size();
         for(int i = 0; i < n; i++) {
            PageEntry thisPageEntry = edgePartition.remove(0);
            for(int page = 0; page < solution.numberOfPages(); page++) {

                if(page != thisPageEntry.page) {
                    PageEntry newPageEntry = new PageEntry(thisPageEntry.a, thisPageEntry.b, page);
                    int numberOfCrossingsOnFirstPage = solution.numberOfCrossingsOnPageForEdge(thisPageEntry);
                    int numberOfCrossingsOnSecondPage = solution.numberOfCrossingsOnPageForEdge(newPageEntry);
                    if( numberOfCrossingsOnSecondPage < numberOfCrossingsOnFirstPage) {
                     	solution.moveEdgeFromPageToPage(thisPageEntry, newPageEntry, numberOfCrossingsOnFirstPage, numberOfCrossingsOnSecondPage);
                        return;
                    }
                }
            }
            edgePartition.add(thisPageEntry);
        }
    }

    @Override
    public void bestNeighbor(KPMPSolution solution) {
//        PageEntry[] newEdgePartition = solution.getEdgePartition().clone();
//        int minimumCrossingsValue = Integer.MAX_VALUE;
//        int minEdgeIndex = 0;
//        int pageNumber = 0;
//
//        for(int edgeIndex = 0; edgeIndex < newEdgePartition.length; edgeIndex++) {
//
//
//            PageEntry thisPageEntry = newEdgePartition[edgeIndex];
//            for(int i = 0; i < solution.numberOfPages(); i++) {
//
//                if(i != thisPageEntry.page) {
//                    PageEntry newPageEntry = new PageEntry(thisPageEntry.a, thisPageEntry.b, i);
//                    newEdgePartition[edgeIndex] = newPageEntry;
//
//                    KPMPSolution neighborSolution = new KPMPSolution(solution.getSpineOrder(), newEdgePartition, solution.numberOfPages());
//                    if (neighborSolution.numberOfCrossings() < minimumCrossingsValue) {
//                        minimumCrossingsValue = neighborSolution.numberOfCrossings();
//                        minEdgeIndex = edgeIndex;
//                        pageNumber = i;
//                    }
//
//                    newEdgePartition[edgeIndex] = thisPageEntry;
//                }
//            }
//        }

//        PageEntry newPageEntry = new PageEntry(newEdgePartition[minEdgeIndex].a, newEdgePartition[minEdgeIndex].b, pageNumber);
//        newEdgePartition[minEdgeIndex] = newPageEntry;
//
//        KPMPSolution bestSolution = new KPMPSolution(solution.getSpineOrder(), newEdgePartition, solution.numberOfPages());

        //return bestSolution;
    }
}