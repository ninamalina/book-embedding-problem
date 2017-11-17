package com.hom.wien.tu.Neighborhood;

import com.hom.wien.tu.Utilities.KPMPSolution;

import java.util.HashMap;
import java.util.Map;
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
        Map<Integer,Integer> initialMap = new HashMap<>(solution.getCrossingsOnPageMap());
        int currentSolutionNumberOfCrossings = solution.calculateCrossingsFromMap();

        Integer[] spineOrder = solution.getSpineOrder();
        int firstIndex = randomNumberGenerator.nextInt(solution.getSpineOrder().length);

        int secondIndex;
        do {
            secondIndex = randomNumberGenerator.nextInt(solution.getSpineOrder().length);
        } while (secondIndex == firstIndex);

        int firstIndexValue = spineOrder[firstIndex];
        spineOrder[firstIndex] = spineOrder[secondIndex];
        spineOrder[secondIndex] = firstIndexValue;

        solution.calculateNumberOfCrossingsForPages();
        int newSolutionNumberOfCrossings = solution.calculateCrossingsFromMap();

        if(newSolutionNumberOfCrossings < currentSolutionNumberOfCrossings) {
            return newSolutionNumberOfCrossings;
        }else {
            firstIndexValue = spineOrder[firstIndex];
            spineOrder[firstIndex] = spineOrder[secondIndex];
            spineOrder[secondIndex] = firstIndexValue;

            solution.setMap(initialMap);
            return currentSolutionNumberOfCrossings;
        }
    }

    @Override
    public int firstNeighbor(KPMPSolution solution) {
        Map<Integer,Integer> initialMap = new HashMap<>(solution.getCrossingsOnPageMap());
        int currentSolutionNumberOfCrossings = solution.calculateCrossingsFromMap();

        Integer[] spineOrder = solution.getSpineOrder();
        int spineLength = spineOrder.length;
        for(int i = 0; i < spineLength; i++) {
            for(int j = i; j < spineLength; j++) {
                if( (i != j)) {

                    int temp = spineOrder[i];
                    spineOrder[i] = spineOrder[j];
                    spineOrder[j] = temp;

                    solution.calculateNumberOfCrossingsForPages();
                    int newSolutionNumberOfCrossings = solution.calculateCrossingsFromMap();

                    if(newSolutionNumberOfCrossings < currentSolutionNumberOfCrossings) {
                        return newSolutionNumberOfCrossings;
                    }

                    temp = spineOrder[i];
                    spineOrder[i] = spineOrder[j];
                    spineOrder[j] = temp;

                    for(Integer key : initialMap.keySet()) {
                        solution.getCrossingsOnPageMap().put(key, initialMap.get(key));
                    }
                }
            }
        }

        return currentSolutionNumberOfCrossings;
    }

    @Override
    public int bestNeighbor(KPMPSolution solution) {
        Map<Integer,Integer> initialMap = new HashMap<>(solution.getCrossingsOnPageMap());

        Integer[] spineOrder = solution.getSpineOrder();
        int spineLength = spineOrder.length;
        int firstIndex = 0;
        int secondIndex = 0;

        solution.calculateNumberOfCrossingsForPages();
        int minimumCrossingsNumber = solution.calculateCrossingsFromMap();

        for(int i = 0; i < spineLength; i++) {
            for(int j = 0; j < spineLength; j++) {
                if( (i != j)) {

                    int temp = spineOrder[i];
                    spineOrder[i] = spineOrder[j];
                    spineOrder[j] = temp;

                    solution.calculateNumberOfCrossingsForPages();
                    int newSolutionNumberOfCrossings = solution.calculateCrossingsFromMap();
                    if(newSolutionNumberOfCrossings < minimumCrossingsNumber) {
                        firstIndex = i;
                        secondIndex = j;

                        minimumCrossingsNumber = newSolutionNumberOfCrossings;
                    }

                    temp = spineOrder[i];
                    spineOrder[i] = spineOrder[j];
                    spineOrder[j] = temp;

                    for(Integer key : initialMap.keySet()) {
                        solution.getCrossingsOnPageMap().put(key, initialMap.get(key));
                    }
                }
            }
        }

        int temp = spineOrder[firstIndex];
        spineOrder[firstIndex] = spineOrder[secondIndex];
        spineOrder[secondIndex] = temp;

        solution.calculateCrossingsFromMap();
        return minimumCrossingsNumber;
    }
}
