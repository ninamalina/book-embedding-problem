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
    public int randomNeighbor(KPMPSolution solution) {
        List<PageEntry> edgePartition = solution.getEdgePartition();
        int randomEdgeIndex = randomNumberGenerator.nextInt(edgePartition.size());
        PageEntry selectedPageEntry = edgePartition.get(randomEdgeIndex);

        int randomPage;
        do {
            randomPage = randomNumberGenerator.nextInt(solution.numberOfPages());
        } while(randomPage == selectedPageEntry.page);

        PageEntry newPageEntry = new PageEntry(selectedPageEntry.a, selectedPageEntry.b, randomPage);
        int numberOfCrossingsOnFirstPage = solution.numberOfCrossingsOnPageForEdge(selectedPageEntry);
        int numberOfCrossingsOnSecondPage = solution.numberOfCrossingsOnPageForEdge(newPageEntry);

        if( numberOfCrossingsOnSecondPage < numberOfCrossingsOnFirstPage) {
        	solution.moveEdgeFromPageToPage(selectedPageEntry, newPageEntry, numberOfCrossingsOnFirstPage, numberOfCrossingsOnSecondPage);
        }

        return solution.calculateCrossingsFromMap();
    }

    @Override
    public int firstNeighbor(KPMPSolution solution) {
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
                        return solution.calculateCrossingsFromMap();
                    }
                }
            }
            edgePartition.add(thisPageEntry);
        }

        return solution.calculateCrossingsFromMap();
    }

    @Override
    public int bestNeighbor(KPMPSolution solution) {
        List<PageEntry> edgePartition = solution.getEdgePartition();
        int n = edgePartition.size();

        int minimumNumberOfCrossings = Integer.MAX_VALUE;
        int minimumNumberOfCrossingsIndex = -1;
        int minimumNumberOfCrossingsPage = -1;
        int numCrossingsFirstPage = -1;
        int numCrossingsSecondPage = 1;

        for(int i = 0; i < n; i++) {
            PageEntry thisPageEntry = edgePartition.get(i);
            for(int page = 0; page < solution.numberOfPages(); page++) {
                if(page != thisPageEntry.page) {
                    PageEntry newPageEntry = new PageEntry(thisPageEntry.a, thisPageEntry.b, page);
                    int numberOfCrossingsOnFirstPage = solution.numberOfCrossingsOnPageForEdge(thisPageEntry);
                    int numberOfCrossingsOnSecondPage = solution.numberOfCrossingsOnPageForEdge(newPageEntry);

                    if( numberOfCrossingsOnSecondPage < numberOfCrossingsOnFirstPage) {
                        int totalNumberOfCrossings = solution.calculateCrossingsFromMap();

                        if(totalNumberOfCrossings < minimumNumberOfCrossings) {
                            minimumNumberOfCrossings = totalNumberOfCrossings;
                            minimumNumberOfCrossingsIndex = i;
                            minimumNumberOfCrossingsPage = page;

                            numCrossingsFirstPage = numberOfCrossingsOnFirstPage;
                            numCrossingsSecondPage = numberOfCrossingsOnSecondPage;
                        }
                    }
                }
            }
        }

        if(minimumNumberOfCrossingsIndex >= 0) {
            PageEntry thisPageEntry = edgePartition.get(minimumNumberOfCrossingsIndex);
            PageEntry newPageEntry = new PageEntry(thisPageEntry.a, thisPageEntry.b, minimumNumberOfCrossingsPage);
            solution.moveEdgeFromPageToPage(thisPageEntry, newPageEntry, numCrossingsFirstPage, numCrossingsSecondPage);
        }

        return solution.calculateCrossingsFromMap();
    }
}