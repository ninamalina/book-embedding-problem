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
    private PageEntry[] edgePartition;
    private int numberOfPages;
    private int numberOfCrossings;
    private boolean crossingsCalculated = false;

    public KPMPSolution(Integer[] spineOrder, PageEntry[] edgePartition, int numberOfPages) {
        this.spineOrder = spineOrder;
        this.edgePartition = edgePartition;
        this.numberOfPages = numberOfPages;

        
        //calculateNumberOfCrossings();
    }
    
    public void printPartition(PageEntry[] edgePartition){
    	for (int i=0; i < edgePartition.length; i++){
    		System.out.println(edgePartition[i]);
    	}
    }
    
    public void printSpineOrder(Integer[] spineOrder){
    	for (int i=0; i < spineOrder.length; i++){
    		System.out.print(spineOrder[i] + " ");
    	}
    }

    public Integer[] getSpineOrder() {
        return spineOrder;
    }

    public PageEntry[] getEdgePartition() {
        return edgePartition;
    }

    public int numberOfPages() {
        return numberOfPages;
    }

    public void setSpineOrder(Integer[] spineOrder) {
        this.spineOrder = spineOrder;
        calculateNumberOfCrossings();
    }

    public void setEdgePartition(PageEntry[] edgePartition) {
        this.edgePartition = edgePartition;
        calculateNumberOfCrossings();
    }

    public int numberOfCrossings() {
    	if (!crossingsCalculated){
    		calculateNumberOfCrossings();
    	}
      
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
                    firstEdgeSpineIndexJ < secondEdgeSpineIndexI &&
                    secondEdgeSpineIndexI < secondEdgeSpineIndexJ) {
                        numberOfCrossings++;
                }
            }
        }
        return numberOfCrossings;   
    }

    public int numberOfCrossingsOnPageForEdge(int page, PageEntry edge){
    	int numberOfCrossings = 0;

        Map<Integer, Integer> spineOrderToVertexIndexMap = new HashMap<>();
        for(int i = 0; i < spineOrder.length; i++) {
            spineOrderToVertexIndexMap.put(spineOrder[i], i);
        }
        
        List<PageEntry> edgesOnThisPage = new ArrayList<>();
        for(PageEntry pageEntry: edgePartition) {
            if (pageEntry.page == page) {
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

                if (firstEdgeSpineIndexI < secondEdgeSpineIndexI &&
                    firstEdgeSpineIndexJ < secondEdgeSpineIndexJ &&
                    firstEdgeSpineIndexI < firstEdgeSpineIndexJ &&
                    firstEdgeSpineIndexJ < secondEdgeSpineIndexI &&
                    secondEdgeSpineIndexI < secondEdgeSpineIndexJ) {
                        numberOfCrossings++;
                }
            
        }
        
        return numberOfCrossings;
    }
    
    private void calculateNumberOfCrossings() {
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
                        firstEdgeSpineIndexJ < secondEdgeSpineIndexI &&
                        secondEdgeSpineIndexI < secondEdgeSpineIndexJ) {
                            numberOfCrossings++;
                    }
                }
            }
        }

        this.numberOfCrossings = numberOfCrossings;
    }
    
    public Map<Integer,Integer> calculateNumberOfCrossingsForPages() {
        
        Map<Integer, Integer> crossingsForPages = new HashMap<>();
        
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
                        firstEdgeSpineIndexJ < secondEdgeSpineIndexI &&
                        secondEdgeSpineIndexI < secondEdgeSpineIndexJ) {
                            numberOfCrossings++;
                    }
                }
            }
            crossingsForPages.put(page,  numberOfCrossings);
        }

        return crossingsForPages;
    }
}