package com.hom.wien.tu.GeneticAlgorithm;

import com.hom.wien.tu.Utilities.KPMPSolution;
import com.hom.wien.tu.Utilities.PageEntry;

import java.util.List;

/**
 * Created by davorsafranko on 12/28/17.
 */
public class GASolution extends KPMPSolution {
    private int error;

    public GASolution(Integer[] spineOrder, List<PageEntry> edgePartition, int numberOfPages) {
        super(spineOrder, edgePartition, numberOfPages);
        this.error = -1;
    }

    public GASolution(KPMPSolution solution) {
        super(solution);
        this.error = -1;
    }

    public int getError() {
        checkNumberOfCrossings();

        return error;
    }

    public double getFitness() {
        checkNumberOfCrossings();

        return 1.0 / (1.0 + error);
    }

    private void checkNumberOfCrossings() {
        if (error == -1) {
            super.calculateNumberOfCrossingsForPages();
            error = super.calculateCrossingsFromMap();
        }
    }
}
