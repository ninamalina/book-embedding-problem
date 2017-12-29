package com.hom.wien.tu.GeneticAlgorithm.Selection;

import com.hom.wien.tu.GeneticAlgorithm.GASolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by davorsafranko on 12/29/17.
 */
public class RandomSelection implements ISelection {
    private Random randomNumberGenerator;

    public RandomSelection(Random randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    @Override
    public List<GASolution> pickParents(List<GASolution> population) {
        List<GASolution> parents = new ArrayList<>(2);

        int firstIndex = randomNumberGenerator.nextInt(population.size());
        int secondIndex = firstIndex;

        while(secondIndex == firstIndex) {
            secondIndex = randomNumberGenerator.nextInt(population.size());
        }

        parents.add(population.get(firstIndex));
        parents.add(population.get(secondIndex));

        return parents;
    }
}
