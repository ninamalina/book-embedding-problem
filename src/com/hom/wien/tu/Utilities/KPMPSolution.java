package com.hom.wien.tu.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by davorsafranko on 11/9/17.
 */
public class KPMPSolution {

    private Integer[] spineOrder;
    private List<PageEntry> edgePartition;
    private int numberOfPages;
    private int numberOfCrossings;
    private Map<Integer,Integer> crossingsOnPage;
    private boolean crossingsCalculated = false;

    public KPMPSolution(Integer[] spineOrder, List<PageEntry> edgePartition, int numberOfPages) {
        this.spineOrder = spineOrder;
        this.edgePartition = edgePartition;
        this.numberOfPages = numberOfPages;

        crossingsOnPage = new HashMap<>();
    }

    public KPMPSolution(KPMPSolution solution) {
        this.spineOrder = solution.spineOrder.clone();
        this.edgePartition = new ArrayList<>(solution.edgePartition);
        this.numberOfPages = solution.numberOfPages();

        this.crossingsOnPage = new HashMap<>();
    }

    public void printPartition(){
    	for (int i=0; i < edgePartition.size(); i++){
    		System.out.println(edgePartition.get(i));
    	}
    }
    
    public void printSpineOrder(){
    	for (int i=0; i < spineOrder.length; i++){
    		System.out.print(spineOrder[i] + " ");
    	}
    }

    public Integer[] getSpineOrder() {
        return spineOrder;
    }

    public List<PageEntry> getEdgePartition() {
        return edgePartition;
    }

    public int numberOfPages() {
        return numberOfPages;
    }

    public void setSpineOrder(Integer[] spineOrder) {
        this.spineOrder = spineOrder;
    }

    public void setEdgePartition(List<PageEntry> edgePartition) {
        this.edgePartition = edgePartition;
    }

    public int numberOfCrossings() {
        return numberOfCrossings;
    }
    
    public int numberOfCrossingsOnPage(int page){
    	int numberOfCrossings = 0;

        Map<Integer, Integer> spineOrderToVertexIndexMap = new HashMap<>();
        for(int i = 0; i < spineOrder.length; i++) {
            spineOrderToVertexIndexMap.put(spineOrder[i], i);
        }
        
        List<PageEntry> edgesOnThisPage = new ArrayList<>();
        for(PageEntry edge: edgePartition) {
            if (edge.page == page) {
            	if (edge.a < edge.b)
            		edgesOnThisPage.add(edge);
            }
        }

        for(PageEntry firstEdge: edgesOnThisPage) {
            for(PageEntry secondEdge: edgesOnThisPage) {
                if (firstEdge == secondEdge) continue;
  
                int firstEdgeSpineIndexI = spineOrderToVertexIndexMap.get(firstEdge.a);
                int firstEdgeSpineIndexJ = spineOrderToVertexIndexMap.get(firstEdge.b);
                int secondEdgeSpineIndexI = spineOrderToVertexIndexMap.get(secondEdge.a);
                int secondEdgeSpineIndexJ = spineOrderToVertexIndexMap.get(secondEdge.b);

                if (firstEdgeSpineIndexJ < firstEdgeSpineIndexI) {
                    int temp = firstEdgeSpineIndexI;
                    firstEdgeSpineIndexI = firstEdgeSpineIndexJ;
                    firstEdgeSpineIndexJ = temp;
                }

                if (secondEdgeSpineIndexJ < secondEdgeSpineIndexI) {
                    int temp = secondEdgeSpineIndexI;
                    secondEdgeSpineIndexI = secondEdgeSpineIndexJ;
                    secondEdgeSpineIndexJ = temp;
                }

                if (firstEdgeSpineIndexI < secondEdgeSpineIndexI &&
                    firstEdgeSpineIndexJ < secondEdgeSpineIndexJ &&
                    firstEdgeSpineIndexI < firstEdgeSpineIndexJ &&
                    firstEdgeSpineIndexJ > secondEdgeSpineIndexI &&
                    secondEdgeSpineIndexI < secondEdgeSpineIndexJ) {
                        numberOfCrossings++;
                }
            }
        }
        return numberOfCrossings;   
    }

    public int numberOfCrossingsOnPageForEdge(PageEntry edge){
    	int numberOfCrossings = 0;

        Map<Integer, Integer> spineOrderToVertexIndexMap = new HashMap<>();
        for(int i = 0; i < spineOrder.length; i++) {
            spineOrderToVertexIndexMap.put(spineOrder[i], i);
        }
        
        List<PageEntry> edgesOnThisPage = new ArrayList<>();
        for(PageEntry pageEntry: edgePartition) {
            if (pageEntry.page == edge.page) {
            	if (pageEntry.a < pageEntry.b)
            		edgesOnThisPage.add(pageEntry);
            }
        }
       
        for(PageEntry firstEdge: edgesOnThisPage) {
                if (firstEdge == edge) continue;
                int firstEdgeSpineIndexI = spineOrderToVertexIndexMap.get(firstEdge.a);
                int firstEdgeSpineIndexJ = spineOrderToVertexIndexMap.get(firstEdge.b);
                int secondEdgeSpineIndexI = spineOrderToVertexIndexMap.get(edge.a);
                int secondEdgeSpineIndexJ = spineOrderToVertexIndexMap.get(edge.b);

                if (firstEdgeSpineIndexJ < firstEdgeSpineIndexI) {
                    int temp = firstEdgeSpineIndexI;
                    firstEdgeSpineIndexI = firstEdgeSpineIndexJ;
                    firstEdgeSpineIndexJ = temp;
                }

                if (secondEdgeSpineIndexJ < secondEdgeSpineIndexI) {
                    int temp = secondEdgeSpineIndexI;
                    secondEdgeSpineIndexI = secondEdgeSpineIndexJ;
                    secondEdgeSpineIndexJ = temp;
                }
                
                if (firstEdgeSpineIndexI > secondEdgeSpineIndexI){
                	int temp = firstEdgeSpineIndexI;
                	firstEdgeSpineIndexI = secondEdgeSpineIndexI;
                	secondEdgeSpineIndexI = temp;
                	temp = firstEdgeSpineIndexJ;
                	firstEdgeSpineIndexJ = secondEdgeSpineIndexJ;
                	secondEdgeSpineIndexJ = temp;
                }

                if (firstEdgeSpineIndexI < secondEdgeSpineIndexI &&
                    firstEdgeSpineIndexJ < secondEdgeSpineIndexJ &&
                    firstEdgeSpineIndexI < firstEdgeSpineIndexJ &&
                    firstEdgeSpineIndexJ > secondEdgeSpineIndexI &&
                    secondEdgeSpineIndexI < secondEdgeSpineIndexJ) {
                        numberOfCrossings++;
                }
        }
        return numberOfCrossings;
    }
    
    public void calculateNumberOfCrossings() {
        int numberOfCrossings = 0;

        Map<Integer, Integer> spineOrderToVertexIndexMap = new HashMap<>();
        for(int i = 0; i < spineOrder.length; i++) {
            spineOrderToVertexIndexMap.put(spineOrder[i], i);
        }

        for(int page = 0; page < numberOfPages; page++) {
            List<PageEntry> edgesOnThisPage = new ArrayList<>();
            for(PageEntry edge: edgePartition) {
                if (edge.page == page && edge.a < edge.b) {
                    edgesOnThisPage.add(edge);
                }
            }

            for(PageEntry firstEdge: edgesOnThisPage) {
                for(PageEntry secondEdge: edgesOnThisPage) {
                    if (firstEdge == secondEdge) continue;
      
                    int firstEdgeSpineIndexI = spineOrderToVertexIndexMap.get(firstEdge.a);
                    int firstEdgeSpineIndexJ = spineOrderToVertexIndexMap.get(firstEdge.b);
                    int secondEdgeSpineIndexI = spineOrderToVertexIndexMap.get(secondEdge.a);
                    int secondEdgeSpineIndexJ = spineOrderToVertexIndexMap.get(secondEdge.b);

                    if (firstEdgeSpineIndexJ < firstEdgeSpineIndexI) {
                        int temp = firstEdgeSpineIndexI;
                        firstEdgeSpineIndexI = firstEdgeSpineIndexJ;
                        firstEdgeSpineIndexJ = temp;
                    }

                    if (secondEdgeSpineIndexJ < secondEdgeSpineIndexI) {
                        int temp = secondEdgeSpineIndexI;
                        secondEdgeSpineIndexI = secondEdgeSpineIndexJ;
                        secondEdgeSpineIndexJ = temp;
                    }

                    if (firstEdgeSpineIndexI < secondEdgeSpineIndexI &&
                        firstEdgeSpineIndexJ < secondEdgeSpineIndexJ &&
                        firstEdgeSpineIndexI < firstEdgeSpineIndexJ &&
                        firstEdgeSpineIndexJ > secondEdgeSpineIndexI &&
                        secondEdgeSpineIndexI < secondEdgeSpineIndexJ) {
                            numberOfCrossings++;
                    }
                }
            }
        }

        this.numberOfCrossings = numberOfCrossings;
    }

    public void setNumberOfCrossings(int numberOfCrossings) {
        this.numberOfCrossings = numberOfCrossings;
    }
    
    public void calculateNumberOfCrossingsForPages() {
        Map<Integer, Integer> spineOrderToVertexIndexMap = new HashMap<>();
        for(int i = 0; i < this.spineOrder.length; i++) {
            spineOrderToVertexIndexMap.put(spineOrder[i], i);
        }

        for(int page = 0; page < numberOfPages; page++) {
        	int numberOfCrossings = 0;
            List<PageEntry> edgesOnThisPage = new ArrayList<>();
            for(PageEntry edge: edgePartition) {
                if (edge.page == page) {
                	if (edge.a < edge.b)
                		edgesOnThisPage.add(edge);
                }
            }

            for(PageEntry firstEdge: edgesOnThisPage) {
                for(PageEntry secondEdge: edgesOnThisPage) {
                    if (firstEdge == secondEdge) continue;

                    int firstEdgeSpineIndexI = spineOrderToVertexIndexMap.get(firstEdge.a);
                    int firstEdgeSpineIndexJ = spineOrderToVertexIndexMap.get(firstEdge.b);
                    int secondEdgeSpineIndexI = spineOrderToVertexIndexMap.get(secondEdge.a);
                    int secondEdgeSpineIndexJ = spineOrderToVertexIndexMap.get(secondEdge.b);

                    if (firstEdgeSpineIndexJ < firstEdgeSpineIndexI) {
                        int temp = firstEdgeSpineIndexI;
                        firstEdgeSpineIndexI = firstEdgeSpineIndexJ;
                        firstEdgeSpineIndexJ = temp;
                    }

                    if (secondEdgeSpineIndexJ < secondEdgeSpineIndexI) {
                        int temp = secondEdgeSpineIndexI;
                        secondEdgeSpineIndexI = secondEdgeSpineIndexJ;
                        secondEdgeSpineIndexJ = temp;
                    }

                    if (firstEdgeSpineIndexI < secondEdgeSpineIndexI &&
                        firstEdgeSpineIndexJ < secondEdgeSpineIndexJ &&
                        firstEdgeSpineIndexI < firstEdgeSpineIndexJ &&
                        firstEdgeSpineIndexJ > secondEdgeSpineIndexI &&
                        secondEdgeSpineIndexI < secondEdgeSpineIndexJ) {
                            numberOfCrossings++;
                    }
                }
            }
            crossingsOnPage.put(page,  numberOfCrossings);
        }
    }

    public void moveEdgeFromPageToPage(PageEntry firstPage, PageEntry secondPage, int numberOfCrossingsFirstPage, int numberOfCrossingsSecondPage) {
        firstPage.page = secondPage.page;
        crossingsOnPage.put(firstPage.page, crossingsOnPage.get(firstPage.page) - numberOfCrossingsFirstPage);
        crossingsOnPage.put(secondPage.page, crossingsOnPage.get(secondPage.page) + numberOfCrossingsSecondPage);
    }

    public Map<Integer,Integer> getCrossingsOnPageMap() {
        return this.crossingsOnPage;
    }
    
    public void printMap(){
    	for(Integer key:crossingsOnPage.keySet()){
    		System.out.println(key + ": " + crossingsOnPage.get(key));
    	}
    }

    public int calculateCrossingsFromMap() {
        int sum = 0;
        for(Integer key: crossingsOnPage.keySet()){
            sum += crossingsOnPage.get(key);
        }
        return sum;
    }
    
    public void setMap(Map<Integer, Integer> newMap){
    	this.crossingsOnPage = newMap;
    }

}