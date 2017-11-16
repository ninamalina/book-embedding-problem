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
    public int randomNeighbor(KPMPSolution solution) {
        int currentSolutionNumberOfCrossings = solution.numberOfCrossings();

        Integer[] spineOrder = solution.getSpineOrder();
        int firstIndex = randomNumberGenerator.nextInt(solution.getSpineOrder().length);

        int secondIndex;
        do {
            secondIndex = randomNumberGenerator.nextInt(solution.getSpineOrder().length);
        } while (secondIndex == firstIndex);

        int firstIndexValue = spineOrder[firstIndex];
        spineOrder[firstIndex] = spineOrder[secondIndex];
        spineOrder[secondIndex] = firstIndexValue;

        solution.calculateNumberOfCrossings();
        int newSolutionNumberOfCrossings = solution.numberOfCrossings();

        if(newSolutionNumberOfCrossings < currentSolutionNumberOfCrossings) {
            return newSolutionNumberOfCrossings;
        }else {
            firstIndexValue = spineOrder[firstIndex];
            spineOrder[firstIndex] = spineOrder[secondIndex];
            spineOrder[secondIndex] = firstIndexValue;

            return currentSolutionNumberOfCrossings;
        }
    }

    @Override
    public int firstNeighbor(KPMPSolution solution) {
        int currentSolutionNumberOfCrossings = solution.numberOfCrossings();
        Integer[] spineOrder = solution.getSpineOrder();

        for(int i = 0; i < solution.numberOfPages(); i++) {
            for(int j = i; j < solution.numberOfPages(); j++) {
                if( (i != j)) {

                    int temp = spineOrder[i];
                    spineOrder[i] = spineOrder[j];
                    spineOrder[j] = temp;

                    int newSolutionNumberOfCrossings = solution.numberOfCrossings();
                    if(newSolutionNumberOfCrossings < currentSolutionNumberOfCrossings) {
                        return newSolutionNumberOfCrossings;
                    }

                    temp = spineOrder[i];
                    spineOrder[i] = spineOrder[j];
                    spineOrder[j] = temp;
                }
            }
        }

        return currentSolutionNumberOfCrossings;
    }

    @Override
    public int bestNeighbor(KPMPSolution solution) {
        Integer[] spineOrder = solution.getSpineOrder();
        int firstIndex = 0;
        int secondIndex = 0;
        int minimumCrossingsNumber = solution.numberOfCrossings();

        for(int i = 0; i < solution.numberOfPages(); i++) {
            for(int j = 0; j < solution.numberOfPages(); j++) {
                if( (i != j)) {

                    int temp = spineOrder[i];
                    spineOrder[i] = spineOrder[j];
                    spineOrder[j] = temp;

                    int newSolutionNumberOfCrossings = solution.numberOfCrossings();
                    if(newSolutionNumberOfCrossings < minimumCrossingsNumber) {
                        firstIndex = i;
                        secondIndex = j;

                        minimumCrossingsNumber = newSolutionNumberOfCrossings;
                    }

                    temp = spineOrder[i];
                    spineOrder[i] = spineOrder[j];
                    spineOrder[j] = temp;
                }
            }
        }

        int temp = spineOrder[firstIndex];
        spineOrder[firstIndex] = spineOrder[secondIndex];
        spineOrder[secondIndex] = temp;

        return minimumCrossingsNumber;
    }
}
