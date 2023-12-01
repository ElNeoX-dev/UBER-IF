package com.malveillance.uberif.model.algo;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Intersection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class TemplateTSP implements TSP {
	private List<Intersection> bestSol;
	protected CityMap g;
	private double bestSolCost;
	private int timeLimit;
	private long startTime;

	public void searchSolution(int timeLimit, CityMap g){
		if (timeLimit <= 0) return;
		startTime = System.currentTimeMillis();
		this.timeLimit = timeLimit;
		this.g = g;
		bestSol = new ArrayList<>();
		Collection<Intersection> unvisited = new ArrayList<Intersection>(g.getNbNodes()-1);
		unvisited.addAll(g.getNodes().keySet());
        unvisited.remove(g.getWarehouse().getIntersection());

		Collection<Intersection> visited = new ArrayList<Intersection>(g.getNbNodes());
		visited.add(g.getWarehouse().getIntersection());

		bestSolCost = Integer.MAX_VALUE;
		branchAndBound(g.getWarehouse().getIntersection(), unvisited, visited, 0);
	}

	public Intersection getSolution(int i){
		if (g != null && i>=0 && i<g.getNbNodes())
			return bestSol.get(i);
		return null;
	}

	public double getSolutionCost(){
		if (g != null)
			return bestSolCost;
		return -1;
	}

	/**
	 * Method that must be defined in TemplateTSP subclasses
	 * @param currentVertex
	 * @param unvisited
	 * @return a lower bound of the cost of paths in <code>g</code> starting from <code>currentVertex</code>, visiting
	 * every vertex in <code>unvisited</code> exactly once, and returning back to vertex <code>0</code>.
	 */
	protected abstract int bound(Intersection currentVertex, Collection<Intersection> unvisited);

	/**
	 * Method that must be defined in TemplateTSP subclasses
	 * @param currentVertex
	 * @param unvisited
	 * @param g
	 * @return an iterator for visiting all vertices in <code>unvisited</code> which are successors of <code>currentVertex</code>
	 */
	protected abstract Iterator<Intersection> iterator(Intersection currentVertex, Collection<Intersection> unvisited, CityMap g);

	/**
	 * Template method of a branch and bound algorithm for solving the TSP in <code>g</code>.
	 * @param currentVertex the last visited vertex
	 * @param unvisited the set of vertex that have not yet been visited
	 * @param visited the sequence of vertices that have been already visited (including currentVertex)
	 * @param currentCost the cost of the path corresponding to <code>visited</code>
	 */
	private void branchAndBound(Intersection currentVertex, Collection<Intersection> unvisited,
			Collection<Intersection> visited, double currentCost){
		if (System.currentTimeMillis() - startTime > timeLimit) return;
	    if (unvisited.isEmpty()){
	    	if (g.hasRoadSegment(currentVertex,g.getWarehouse().getIntersection())){
	    		if (currentCost+g.getDistance(currentVertex,g.getWarehouse().getIntersection()) < bestSolCost){
					bestSol.clear();
					bestSol.addAll(visited);
	    			bestSolCost = currentCost+g.getDistance(currentVertex,g.getWarehouse().getIntersection());
	    		}
	    	}
	    } else if (currentCost+bound(currentVertex,unvisited) < bestSolCost){
	        Iterator<Intersection> it = iterator(currentVertex, unvisited, g);
	        while (it.hasNext()){
				Intersection nextVertex = it.next();
	        	visited.add(nextVertex);
	            unvisited.remove(nextVertex);
	            branchAndBound(nextVertex, unvisited, visited,
	            		currentCost+g.getDistance(currentVertex, nextVertex));
	            visited.remove(nextVertex);
	            unvisited.add(nextVertex);
	        }
	    }
	}

}
