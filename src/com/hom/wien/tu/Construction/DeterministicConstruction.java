package com.hom.wien.tu.Construction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.hom.wien.tu.Utilities.ArrayIndexComparator;
import com.hom.wien.tu.Utilities.KPMPInstance;
import com.hom.wien.tu.Utilities.KPMPSolution;
import com.hom.wien.tu.Utilities.PageEntry;
import java.util.Collections;
import java.util.Random;
public class DeterministicConstruction {
	
	public KPMPSolution buildSolution(KPMPInstance instance){
		int K = instance.getNumberOfPages();
		List<List<Integer>> adjList = instance.getAdjacencyList();
		ArrayIndexComparator comparator = new ArrayIndexComparator(adjList);
		Integer[] indexes = comparator.createIndexArray();
		Arrays.sort(indexes, comparator);
		List<Integer> spineOrder = new ArrayList<Integer>();
		ArrayList<PageEntry> edgesPartition = new ArrayList<>();

		List<Integer> verticeOrder = new ArrayList<Integer>();
		for(int i = 0; i < indexes.length/2; i++){
			verticeOrder.add(indexes[i]);
			verticeOrder.add(indexes[indexes.length-i-1]);
		}
		if (indexes.length%2==1){
			verticeOrder.add(indexes[indexes.length/2]);
		}
		
		spineOrder.add(indexes[0]);
		KPMPSolution currentSolution = new KPMPSolution(spineOrder.stream().toArray(Integer[]::new), edgesPartition, K);;
		currentSolution.calculateNumberOfCrossingsForPages();
		
		for (Integer node:verticeOrder){
			List<Integer> neighbours = adjList.get(node);
			
			if (!spineOrder.contains(node)){ 
				int n = spineOrder.size();
				
				int bestCrossings = Integer.MAX_VALUE;
				KPMPSolution bestSolution = null;
				
				for (int spineInsert=0; spineInsert < n; spineInsert++){ //check which place is best to insert it
					List<Integer> tempSpineOrder = new ArrayList<>(spineOrder);
					tempSpineOrder.add(spineInsert, node);
					
					KPMPSolution tempSolution = new KPMPSolution(tempSpineOrder.stream().toArray(Integer[]::new), new ArrayList<>(currentSolution.getEdgePartition()), K);
					tempSolution.calculateNumberOfCrossingsForPages();
					
					putNeighboursToPages(node, neighbours, tempSolution);
					int currentCrossings = currentSolution.calculateCrossingsFromMap();
					if (currentCrossings < bestCrossings){
						bestCrossings = currentCrossings;
						bestSolution = tempSolution;
						spineOrder = new ArrayList<>(Arrays.asList(tempSolution.getSpineOrder()));
					}
				}
				currentSolution = bestSolution;
			} else {
				putNeighboursToPages(node, neighbours, currentSolution);
				spineOrder = new ArrayList<>(Arrays.asList(currentSolution.getSpineOrder()));
			}
		}
		return currentSolution;
	}

	
	public void putNeighboursToPages(Integer node, List<Integer> neighbours, KPMPSolution currentSolution) {
		
		List<Integer> spineOrder = new ArrayList<>(Arrays.asList(currentSolution.getSpineOrder()));
		
		for(int neighbour: neighbours){
			
			if (!spineOrder.contains(neighbour)){
				spineOrder.add(neighbour);
				currentSolution.setSpineOrder(spineOrder.stream().toArray(Integer[]::new));
				currentSolution.calculateNumberOfCrossingsForPages();
			}
			
			if (neighbour < node) continue; //to avoid duplicated edges 

			int bestPage = 0;
			int bestCrossings = Integer.MAX_VALUE;
			int newCrossingsForBestPage = Integer.MAX_VALUE;	
			
			int oldNumberOfCrossings = currentSolution.calculateCrossingsFromMap();
			
			for (int page=0; page < currentSolution.numberOfPages(); page++){
				ArrayList<PageEntry> tempEdgesPartition = new ArrayList(currentSolution.getEdgePartition());
				PageEntry newEdge = new PageEntry(node, neighbour, page);
				tempEdgesPartition.add(newEdge);
				KPMPSolution tempSolution = new KPMPSolution(spineOrder.stream().toArray(Integer[]::new), tempEdgesPartition, currentSolution.numberOfPages());
				int additionalNumberOfcrossingsForPage = tempSolution.numberOfCrossingsOnPageForEdge(newEdge);
				Map<Integer,Integer> tempMapOfCrossings = new HashMap(currentSolution.getCrossingsOnPageMap());
				
				if (tempMapOfCrossings.containsKey(page)){
					tempMapOfCrossings.put(page, tempMapOfCrossings.get(page)+additionalNumberOfcrossingsForPage);
				}else{
					tempMapOfCrossings.put(page, additionalNumberOfcrossingsForPage);
				}
				tempSolution.setMap(tempMapOfCrossings);
				
				int tempNumberOfCrossings = tempSolution.calculateCrossingsFromMap();
				
				if (tempNumberOfCrossings < bestCrossings){
					bestPage = page;
					bestCrossings = tempNumberOfCrossings;
					newCrossingsForBestPage = tempMapOfCrossings.get(page);
					
					if(tempNumberOfCrossings == oldNumberOfCrossings) break;
				}
				
			}
			currentSolution.getEdgePartition().add(new PageEntry(node,  neighbour,  bestPage));
			currentSolution.getCrossingsOnPageMap().put(bestPage, newCrossingsForBestPage);
			
		}
	}
		
}