package com.malveillance.uberif.model.algo;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Delivery;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.Tour;
import javafx.util.Pair;

import java.util.Date;

/**
 * The class represents a template for solving the TSP.
 */
public interface TSP {
	/**
	 * Search for a shortest cost hamiltonian circuit in <code>g</code> within <code>timeLimit</code> milliseconds
	 * (returns the best found tour whenever the time limit is reached)
	 * Warning: The computed tour always start from vertex 0
	 * @param timeLimit
	 * @param g
	 */
	public void searchSolution(int timeLimit, CityMap g, Tour t,Date startingDate);
	
	/**
	 * @param i
	 * @return the ith visited vertex in the solution computed by <code>searchSolution</code> 
	 * (-1 if <code>searcheSolution</code> has not been called yet, or if i < 0 or i >= g.getNbSommets())
	 */
	public Pair<Intersection, Date> getSolution(int i);
	
	/** 
	 * @return the total cost of the solution computed by <code>searchSolution</code> 
	 * (-1 if <code>searcheSolution</code> has not been called yet).
	 */
	public double getSolutionCost();

}
