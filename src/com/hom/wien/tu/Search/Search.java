package com.hom.wien.tu.Search;

import com.hom.wien.tu.Utilities.KPMPSolution;

/**
 * Created by davorsafranko on 11/9/17.
 */
public abstract class Search {

    protected long startTime;
    protected KPMPSolution currentSolution;
    protected KPMPSolution initialSolution;

    public Search(KPMPSolution initialSolution) {
        this.initialSolution = initialSolution;
    }

    public final void search() {
        prepareSearch();
        this.startTime = System.currentTimeMillis();

        currentSolution = initialSolution;
        while(shouldContinue()) {
            findSolution();
        }
    }

    public KPMPSolution getBestSolution() {
        return currentSolution;
    }

    protected abstract void prepareSearch();
    protected abstract void findSolution();
    protected abstract boolean shouldContinue();
}
