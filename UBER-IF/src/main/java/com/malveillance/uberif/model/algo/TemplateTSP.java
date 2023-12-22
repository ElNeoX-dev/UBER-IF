package com.malveillance.uberif.model.algo;

import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.Delivery;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

/**
 * The class represents a template for solving the TSP.
 */
public abstract class TemplateTSP implements TSP {

	/**
	 * The best solution found during the search.
	 */
	private List<Pair<Intersection, Date>> bestSol;

	/**
	 * The city map on which the TSP is performed.
	 */
	protected CityMap g;

	/**
	 * The cost of the best solution found.
	 */
	private double bestSolCost;

	/**
	 * The time limit for the TSP search.
	 */
	private int timeLimit;

	/**
	 * The start time of the TSP search.
	 */
	private long startTime;

	/**
	 * The current time during the TSP search.
	 */
	private double currentTime;

	/**
	 * The default starting hour for delivery.
	 */
	private static final int deliveryStartTime = 8;

	/**
	 * The number of seconds in an hour.
	 */
	private static final int secondsInHour = 3600;

	/**
	 * Initiates the TSP search with the specified time limit, city map, tour, and starting date
	 * @param timeLimit     the time limit for the TSP search
	 * @param g             the city map
	 * @param t             the tour
	 * @param startingDate  the starting date for the TSP search
	 */
	public void searchSolution(int timeLimit, CityMap g, Tour t, Date startingDate) {
		if (timeLimit <= 0) return;
		startTime = System.currentTimeMillis();
		this.timeLimit = timeLimit;
		this.g = g;
		bestSol = new ArrayList<>();
		Collection<Delivery> unvisited = new ArrayList<>(g.getNbNodes() - 1);
		unvisited.addAll(t.getDeliveries());
		unvisited.remove(t.getStartingPoint());

		Collection<Pair<Intersection, Date>> visited = new ArrayList<>(g.getNbNodes());
		visited.add(new Pair<>(t.getStartingPoint().getIntersection(), startingDate));

		bestSolCost = Integer.MAX_VALUE;
		branchAndBound(t.getStartingPoint(), unvisited, visited, 0);
	}

	/**
	 * Gets the solution at the specified index
	 * @param i the index of the solution
	 * @return the solution at the specified index
	 */
	public Pair<Intersection, Date> getSolution(int i) {
		if (g != null && i >= 0 && i < g.getNbNodes())
			return bestSol.get(i);
		return null;
	}

	/**
	 * Gets the cost of the best solution found
	 * @return the cost of the best solution
	 */
	public double getSolutionCost() {
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
	protected abstract Iterator<Delivery> iterator(Delivery currentVertex, Collection<Delivery> unvisited, CityMap g, double currentTime);

	/**
	 * Template method of a branch and bound algorithm for solving the TSP in <code>g</code>.
	 * @param currentVertex the last visited vertex
	 * @param unvisited the set of vertex that have not yet been visited
	 * @param visited the sequence of vertices that have been already visited (including currentVertex)
	 * @param currentCost the cost of the path corresponding to <code>visited</code>
	 */
	private void branchAndBound(Delivery currentVertex, Collection<Delivery> unvisited,
			Collection<Pair<Intersection, Date>> visited, double currentCost){
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
	        Iterator<Delivery> it = iterator(currentVertex, unvisited, g, currentTime);
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

				if (arrivalTime < timeStart) {
					arrivalTime = timeStart;
				}


				if (arrivalTime >= timeStart && arrivalTime <= timeEnd) {
					Calendar c = Calendar.getInstance();
					calendar.set(Calendar.HOUR_OF_DAY, TimeWindow.defaultStartingHour);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.add(Calendar.SECOND, (int)arrivalTime);
					Date timeDelivery = calendar.getTime();
					Pair<Intersection,Date> deliveryDate = new Pair<>(nextVertex.getIntersection(),timeDelivery);
					visited.add(deliveryDate);
					unvisited.remove(nextVertex);
					double previousTime = currentTime;
					currentTime = arrivalTime + 300;
					branchAndBound(nextVertex, unvisited, visited,
							currentCost + travelTime);
					currentTime = previousTime;
					visited.remove(deliveryDate);
					unvisited.add(nextVertex);
				}
	        }
	    }
	}

}
