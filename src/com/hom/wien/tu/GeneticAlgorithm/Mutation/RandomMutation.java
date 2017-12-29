package com.hom.wien.tu.GeneticAlgorithm.Mutation;

import com.hom.wien.tu.GeneticAlgorithm.GASolution;
import com.hom.wien.tu.Utilities.PageEntry;

import java.util.List;
import java.util.Random;

/**
 * Created by davorsafranko on 12/29/17.
 */
public class RandomMutation implements IMutation {
    private Random randomNumberGenerator;
    private double spineOrderMutationProbability;
    private double pageNumberMutationProbability;

    public RandomMutation(Random randomNumberGenerator, double spineOrderMutationProbability, double pageNumberMutationProbability) {
        this.randomNumberGenerator = randomNumberGenerator;
        this.spineOrderMutationProbability = spineOrderMutationProbability;
        this.pageNumberMutationProbability = pageNumberMutationProbability;
    }

    @Override
    public void mutate(GASolution solution) {
        if (randomNumberGenerator.nextDouble() < spineOrderMutationProbability) {
            Integer[] spineOrder = solution.getSpineOrder();

            int firstIndex = randomNumberGenerator.nextInt(spineOrder.length);
            int secondIndex = firstIndex;

            while(secondIndex == firstIndex) {
                secondIndex = randomNumberGenerator.nextInt(solution.getSpineOrder().length);
            }

            int temp = spineOrder[firstIndex];
            spineOrder[firstIndex] = spineOrder[secondIndex];
            spineOrder[secondIndex] = temp;
        }

        for(PageEntry pageEntry: solution.getEdgePartition()) {
            if(randomNumberGenerator.nextDouble() < pageNumberMutationProbability) {
                int pageNumber = pageEntry.page;
                while(pageNumber == pageEntry.page) {
                    pageNumber = randomNumberGenerator.nextInt(solution.numberOfPages());
                }

                pageEntry.page = pageNumber;
            }
        }
    }
}
