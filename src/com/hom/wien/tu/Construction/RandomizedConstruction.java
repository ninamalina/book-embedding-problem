package com.hom.wien.tu.Construction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hom.wien.tu.Utilities.ArrayIndexComparator;
import com.hom.wien.tu.Utilities.KPMPInstance;
import com.hom.wien.tu.Utilities.KPMPSolution;
import com.hom.wien.tu.Utilities.PageEntry;

public class RandomizedConstruction {
	
	private DeterministicConstruction dc = new DeterministicConstruction();

	public KPMPSolution buildSolution(KPMPInstance instance){

		int K = instance.getNumberOfPages();
		List<List<Integer>> adjList = instance.getAdjacencyList();
		
		// order vertices by their degree ascending 
		ArrayIndexComparator comparator = new ArrayIndexComparator(adjList);
		Integer[] indexes = comparator.createIndexArray();
		Arrays.sort(indexes, comparator);
		
		List<Integer> spineOrder = new ArrayList<Integer>();
		ArrayList<PageEntry> edgesPartition = new ArrayList<>();		
		for(Integer index:indexes){
			spineOrder.add(index);
		}
		
		Collections.shuffle(spineOrder);
		
		KPMPSolution currentSolution = new KPMPSolution(spineOrder.stream().toArray(Integer[]::new), edgesPartition, K);;
		
		for (Integer index:indexes){
			List<Integer> neighbours = adjList.get(index);
			Collections.shuffle(neighbours);
			dc.putNeighboursToPages(index, neighbours, currentSolution);
			spineOrder = new ArrayList<>(Arrays.asList(currentSolution.getSpineOrder()));
		}
		
		return currentSolution;
	}

}