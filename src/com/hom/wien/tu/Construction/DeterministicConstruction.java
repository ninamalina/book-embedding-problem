package com.hom.wien.tu.Construction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		if (random){
			Collections.shuffle(spineOrder);
		}

		KPMPSolution currentSolution = new KPMPSolution(spineOrder.stream().toArray(Integer[]::new), edgesPartition, K);;
		currentSolution.calculateNumberOfCrossingsForPages();
		for (Integer index:indexes){
			if (!spineOrder.contains(index)){
				spineOrder.add(index);
				currentSolution.setSpineOrder(spineOrder.stream().toArray(Integer[]::new));
			}
			List<Integer> neighbours = adjList.get(index);
			if (random){
				Collections.shuffle(neighbours);
			}
			for(int neighbour: neighbours){
				if (!spineOrder.contains(neighbour)){
					spineOrder.add(neighbour);
					currentSolution.setSpineOrder(spineOrder.stream().toArray(Integer[]::new));
				}
				
				if (neighbour < index){
					continue;
				}
				int bestPage = 0;
				int bestCrossings = -1;
				int newCrossingsForPageK = -1;				
				int oldNumberOfCrossings = currentSolution.calculateCrossingsFromMap();
				
				for (int page=0; page < K; page++){
					ArrayList<PageEntry> newEdgesPartition = new ArrayList(edgesPartition);
					PageEntry newEdge = new PageEntry(index, neighbour, page);
					newEdgesPartition.add(newEdge);
					KPMPSolution newSolution = new KPMPSolution(spineOrder.stream().toArray(Integer[]::new), newEdgesPartition, K);
					int additionalNumberOfcrossingsForPage = newSolution.numberOfCrossingsOnPageForEdge(newEdge);
					Map<Integer,Integer> newMapOfCrossings = new HashMap(currentSolution.getCrossingsOnPageMap());
					if (newMapOfCrossings.containsKey(page)){
						newMapOfCrossings.put(page, newMapOfCrossings.get(page)+additionalNumberOfcrossingsForPage);
					}else{
						newMapOfCrossings.put(page, additionalNumberOfcrossingsForPage);
					}
					newSolution.setMap(newMapOfCrossings);
					
					int newNumberOfCrossings = newSolution.calculateCrossingsFromMap();
					
					if (bestCrossings == -1 || newNumberOfCrossings < bestCrossings){
						bestPage = page;
						bestCrossings = newNumberOfCrossings;
						newCrossingsForPageK = newMapOfCrossings.get(page)+additionalNumberOfcrossingsForPage;
						
						if(newNumberOfCrossings == oldNumberOfCrossings){
							break;
						}
					}
				}
				
				edgesPartition.add(new PageEntry(index,  neighbour,  bestPage));
				currentSolution.getCrossingsOnPageMap().put(bestPage, newCrossingsForPageK);
			}
		}
		
		currentSolution.setEdgePartition(edgesPartition);
		return currentSolution;
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