package com.hom.wien.tu.GeneticAlgorithm.Selection;

import com.hom.wien.tu.GeneticAlgorithm.GASolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by davorsafranko on 12/29/17.
 */
public class RouletteWheelSelection implements ISelection {
    private Random randomNumberGenerator;

    public RouletteWheelSelection(Random randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    @Override
    public List<GASolution> pickParents(List<GASolution> population) {
        List<GASolution> parents = new ArrayList<>(2);

        List<GASolution> thisP = new ArrayList<>(population.size());
        for(int i = 0; i < population.size(); i++){
            thisP.add(population.get(i));
        }

        double sumOfFitness = calculateFitnessSum(thisP);
        parents.add(pickParent(thisP, sumOfFitness));

        sumOfFitness = calculateFitnessSum(thisP);
        parents.add(pickParent(thisP, sumOfFitness));

        return parents;
    }

    private GASolution pickParent(List<GASolution> population, double sumOfFitness){
        double pickElement = randomNumberGenerator.nextDouble() * sumOfFitness;

        double sum = 0;
        for(GASolution unit : population){
            sum += unit.getFitness();
            if(sum > pickElement){
                population.remove(unit);
                return new GASolution(unit);
            }
        }
        return null;
    }

    private static double calculateFitnessSum(List<GASolution> population){
        double sumOfFitness = 0;
        for (GASolution unit : population){
            sumOfFitness += unit.getFitness();
        }

        return sumOfFitness;
    }
}
