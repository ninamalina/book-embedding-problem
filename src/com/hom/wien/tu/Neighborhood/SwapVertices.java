package com.hom.wien.tu.Neighborhood;

import com.hom.wien.tu.Utilities.KPMPSolution;
import java.util.Random;

/**
 * Created by davorsafranko on 11/10/17.
 */
public class SwapVertices implements INeighborhood {

    private Random randomNumberGenerator;

    public SwapVertices() {
        this.randomNumberGenerator = new Random();
    }

    @Override
    public KPMPSolution randomNeighbor(KPMPSolution solution) {
        int firstIndex = randomNumberGenerator.nextInt(solution.getSpineOrder().length);

        int secondIndex;
        do {
            secondIndex = randomNumberGenerator.nextInt(solution.getSpineOrder().length);
        } while (secondIndex == firstIndex);

        Integer[] newSpineOrder = solution.getSpineOrder().clone();
        int firstIndexValue = newSpineOrder[firstIndex];
        newSpineOrder[firstIndex] = newSpineOrder[secondIndex];
        newSpineOrder[secondIndex] = firstIndexValue;

        KPMPSolution randomSolution = new KPMPSolution(newSpineOrder, solution.getEdgePartition(), solution.numberOfPages());
        return randomSolution;
    }

    @Override
    public KPMPSolution firstNeighbor(KPMPSolution solution) {
        Integer[] newSpineOrder = solution.getSpineOrder().clone();

        for(int i = 0; i < solution.numberOfPages(); i++) {
            for(int j = i; j < solution.numberOfPages(); j++) {
                if( (i != j)) {

                    int temp = newSpineOrder[i];
                    newSpineOrder[i] = newSpineOrder[j];
                    newSpineOrder[j] = temp;

                    KPMPSolution newSolution = new KPMPSolution(newSpineOrder, solution.getEdgePartition(), solution.numberOfPages());
                    if(newSolution.numberOfCrossings() < solution.numberOfCrossings()) {
                        return newSolution;
                    }
                }
            }
        }

        return solution;
    }

    @Override
    public KPMPSolution bestNeighbor(KPMPSolution solution) {
        Integer[] newSpineOrder = solution.getSpineOrder().clone();
        int firstIndex = 0;
        int secondIndex = 0;
        int minimumCrossingsNumber = solution.numberOfCrossings();

        for(int i = 0; i < solution.numberOfPages(); i++) {
            for(int j = 0; j < solution.numberOfPages(); j++) {
                if( (i != j)) {

                    int temp = newSpineOrder[i];
                    newSpineOrder[i] = newSpineOrder[j];
                    newSpineOrder[j] = temp;

                    KPMPSolution newSolution = new KPMPSolution(newSpineOrder, solution.getEdgePartition(), solution.numberOfPages());
                    if(newSolution.numberOfCrossings() < minimumCrossingsNumber) {
                        firstIndex = i;
                        secondIndex = j;

                        minimumCrossingsNumber = newSolution.numberOfCrossings();
                    }

                    temp = newSpineOrder[i];
                    newSpineOrder[i] = newSpineOrder[j];
                    newSpineOrder[j] = temp;
                }
            }
        }

        int temp = newSpineOrder[firstIndex];
        newSpineOrder[firstIndex] = newSpineOrder[secondIndex];
        newSpineOrder[secondIndex] = temp;

        KPMPSolution bestSolution = new KPMPSolution(newSpineOrder, solution.getEdgePartition(), solution.numberOfPages());
        return bestSolution;
    }
}
