package com.hom.wien.tu.Search;

import com.hom.wien.tu.Utilities.KPMPSolution;

/**
 * Created by davorsafranko on 11/9/17.
 */
public abstract class Search {

    protected long startTime;
    protected KPMPSolution currentSolution;
    protected KPMPSolution initialSolution;
    protected int iterationsWithoutImprovement = 0;

    public Search(KPMPSolution initialSolution) {
        currentSolution = initialSolution;
        this.initialSolution = initialSolution;
    }

    public final void search() {
        prepareSearch();
        this.startTime = System.currentTimeMillis();

        while(shouldContinue() && (iterationsWithoutImprovement <= 2000)) {
            boolean improved = findSolution();
            if(improved) {
                //System.out.println("Current number of crossings: " + currentSolution.calculateCrossingsFromMap());
                iterationsWithoutImprovement = 0;
            }else {
                iterationsWithoutImprovement++;
            }
        }
    }

    public KPMPSolution getBestSolution() {
        return currentSolution;
    }

    protected abstract void prepareSearch();
    protected abstract boolean findSolution();
    protected abstract boolean shouldContinue();
}
