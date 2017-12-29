package com.hom.wien.tu.GeneticAlgorithm.CrossOver;

import com.hom.wien.tu.GeneticAlgorithm.GASolution;

/**
 * Created by davorsafranko on 12/28/17.
 */
public interface ICrossOver {
    GASolution crossOver(GASolution firstParent, GASolution secondParent);
}
