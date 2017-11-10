package com.hom.wien.tu.Search;

import com.hom.wien.tu.Utilities.KPMPSolution;

/**
 * Created by davorsafranko on 11/9/17.
 */
public interface ISearch {

    KPMPSolution search();
    boolean shouldContinue();
}
