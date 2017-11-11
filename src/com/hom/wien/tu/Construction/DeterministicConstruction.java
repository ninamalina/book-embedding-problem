package com.hom.wien.tu.Construction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.hom.wien.tu.Utilities.KPMPInstance;
import com.hom.wien.tu.Utilities.KPMPSolution;
import com.hom.wien.tu.Utilities.PageEntry;
import java.util.Collections;

public class DeterministicConstruction {
	
	public KPMPSolution buildSolution(KPMPInstance instance, boolean random){
		int n = instance.getNumVertices();
		int K = instance.getNumberOfPages();
		List<List<Integer>> adjList = instance.getAdjacencyList();
		ArrayIndexComparator comparator = new ArrayIndexComparator(adjList);
		Integer[] indexes = comparator.createIndexArray();
		Arrays.sort(indexes, comparator);

		List<Integer> spineOrder = new ArrayList<Integer>();
		ArrayList<PageEntry> edgesPartition = new ArrayList<>();
		
		for(Integer i:indexes){
			spineOrder.add(i);
		}
		
		if (random){
			Collections.shuffle(spineOrder);
		}

		for (Integer index:spineOrder){
			List<Integer> neighbours = adjList.get(index);
			if (random){
				Collections.shuffle(neighbours);
			}
			for(int neighbour: neighbours){
				if (neighbour < index){
					continue;
				}
				int bestPage = 0;
				int bestCrossings = -1;
				
				for (int page=0; page< K; page++){
					ArrayList<PageEntry> newEdgesPartition = new ArrayList(edgesPartition);
					newEdgesPartition.add(new PageEntry(index, neighbour, page));
					KPMPSolution newSolution = new KPMPSolution(spineOrder.stream().toArray(Integer[]::new), newEdgesPartition.stream().toArray(PageEntry[]::new), K);
					if (bestCrossings == -1 || newSolution.numberOfCrossings() < bestCrossings){
						bestPage = page;
						bestCrossings = newSolution.numberOfCrossings();
					}
				}
				
				edgesPartition.add(new PageEntry(index,  neighbour,  bestPage));
			}
		}
		
		KPMPSolution solution = new KPMPSolution(spineOrder.stream().toArray(Integer[]::new), edgesPartition.stream().toArray(PageEntry[]::new), K);
		return solution;
	}
	
	public class ArrayIndexComparator implements Comparator<Integer>
	{
	    private final List<List<Integer>> array;

	    public ArrayIndexComparator(List<List<Integer>> array)
	    {
	        this.array = array;
	    }

	    public Integer[] createIndexArray()
	    {
	        Integer[] indexes = new Integer[array.size()];
	        for (int i = 0; i < array.size(); i++)
	        {
	            indexes[i] = i; // Autoboxing
	        }
	        return indexes;
	    }

	    @Override
	    public int compare(Integer index1, Integer index2)
	    {
	         // Autounbox from Integer to int to use as array indexes
	        return array.get(index1).size() - array.get(index2).size();
	    }
	}
}