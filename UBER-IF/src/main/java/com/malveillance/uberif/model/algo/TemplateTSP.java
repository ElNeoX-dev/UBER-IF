package com.malveillance.uberif.model.algo;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Delivery;
import com.malveillance.uberif.model.Tour;
import com.malveillance.uberif.model.Delivery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

public abstract class TemplateTSP implements TSP {
	private List<Delivery> bestSol;
	protected CityMap g;
	private double bestSolCost;
	private int timeLimit;
	private long startTime;
	private double currentTime;
	private static final int deliveryStartTime = 8;
	private static final int secondsInHour = 3600;

	public void searchSolution(int timeLimit, CityMap g,Tour t){
		if (timeLimit <= 0) return;
		startTime = System.currentTimeMillis();
		this.timeLimit = timeLimit;
		this.g = g;
		bestSol = new ArrayList<>();
		Collection<Delivery> unvisited = new ArrayList<>(g.getNbNodes()-1);
		unvisited.addAll(t.getDeliveries());
        unvisited.remove(t.getStartingPoint());

		Collection<Delivery> visited = new ArrayList<>(g.getNbNodes());
		visited.add(t.getStartingPoint());

		bestSolCost = Integer.MAX_VALUE;
		branchAndBound(t.getStartingPoint(), unvisited, visited, 0);
	}

	public Delivery getSolution(int i){
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
	protected abstract int bound(Delivery currentVertex, Collection<Delivery> unvisited);

	/**
	 * Method that must be defined in TemplateTSP subclasses
	 * @param currentVertex
	 * @param unvisited
	 * @param g
	 * @return an iterator for visiting all vertices in <code>unvisited</code> which are successors of <code>currentVertex</code>
	 */
	protected abstract Iterator<Delivery> iterator(Delivery currentVertex, Collection<Delivery> unvisited, CityMap g);

	/**
	 * Template method of a branch and bound algorithm for solving the TSP in <code>g</code>.
	 * @param currentVertex the last visited vertex
	 * @param unvisited the set of vertex that have not yet been visited
	 * @param visited the sequence of vertices that have been already visited (including currentVertex)
	 * @param currentCost the cost of the path corresponding to <code>visited</code>
	 */
	private void branchAndBound(Delivery currentVertex, Collection<Delivery> unvisited,
			Collection<Delivery> visited, double currentCost){
		if (System.currentTimeMillis() - startTime > timeLimit) return;
	    if (unvisited.isEmpty()){
	    	if (g.hasRoadSegment(currentVertex.getIntersection(),g.getWarehouse().getIntersection())){
	    		if (currentCost+g.getDistance(currentVertex.getIntersection(),g.getWarehouse().getIntersection()) < bestSolCost){
					bestSol.clear();
					bestSol.addAll(visited);
	    			bestSolCost = currentCost+g.getDistance(currentVertex.getIntersection(),g.getWarehouse().getIntersection());
	    		}
	    	}
	    } else if (currentCost+bound(currentVertex,unvisited) < bestSolCost){
	        Iterator<Delivery> it = iterator(currentVertex, unvisited, g);
	        while (it.hasNext()){
				Delivery nextVertex = it.next();
				double travelTime = g.getDistance(currentVertex.getIntersection(), nextVertex.getIntersection());
				double arrivalTime = currentTime + travelTime;

				Date nextVertexStart = nextVertex.getTimeWindow().getStartingTime();
				Date nextVertexEnd = nextVertex.getTimeWindow().getEndingTime();

				Calendar calendar = Calendar.getInstance();

				calendar.setTime(nextVertexEnd);
				int timeEnd = (calendar.get(Calendar.HOUR_OF_DAY) - deliveryStartTime) * secondsInHour;

				calendar.setTime(nextVertexStart);
				int timeStart = (calendar.get(Calendar.HOUR_OF_DAY) - deliveryStartTime) * secondsInHour;


				if (arrivalTime >= timeStart && arrivalTime <= timeEnd) {
					visited.add(nextVertex);
					unvisited.remove(nextVertex);
					double previousTime = currentTime;
					currentTime = arrivalTime;
					branchAndBound(nextVertex, unvisited, visited,
							currentCost + travelTime);
					currentTime = previousTime;
					visited.remove(nextVertex);
					unvisited.add(nextVertex);
				}
	        }
	    }
	}

}
