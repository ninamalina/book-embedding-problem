package com.hom.wien.tu.GeneticAlgorithm.Selection;

import com.hom.wien.tu.GeneticAlgorithm.GASolution;

import java.util.List;

/**
 * Created by davorsafranko on 12/29/17.
 */
public interface ISelection {
    List<GASolution> pickParents(List<GASolution> population);
}
