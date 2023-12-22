package com.malveillance.uberif.model.algo;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Delivery;
import com.malveillance.uberif.model.TimeWindow;

import java.util.*;

/**
 * The class represents a template for solving the TSP.
 */
public class TSP1 extends TemplateTSP {

	/**
	 * @param currentVertex current vertex
	 * @param unvisited   set of unvisited vertices
	 * @return the lower bound of the cost of paths currentVertex
	 */
	@Override
	protected int bound(Delivery currentVertex, Collection<Delivery> unvisited) {
		if (unvisited.isEmpty()) {
			return 0;
		}

		double bound = CityMap.INFINITE_LENGTH;
		for (Delivery v : unvisited) {
			double distance = g.getDistance(currentVertex.getIntersection(), v.getIntersection());
			if (distance < bound) {
				bound = distance;
			}
		}

		for (Delivery v1 : unvisited) {
			double nearestNeighborDistance = CityMap.INFINITE_LENGTH;
			for (Delivery v2 : unvisited) {
				if (!v1.equals(v2)) {
					double distance = g.getDistance(v1.getIntersection(), v2.getIntersection());
					if (distance < nearestNeighborDistance) {
						nearestNeighborDistance = distance;
					}
				}
			}
			if (nearestNeighborDistance < CityMap.INFINITE_LENGTH) {
				bound += nearestNeighborDistance;
			}
		}

		return (int)Math.ceil(bound);
	}

	/**
	 * @param currentVertex current vertex
	 * @param unvisited    set of unvisited vertices
	 * @param g 		  the graph
	 * @param currentTime the current time
	 * @return an iterator to traverse the set of vertices in unvisited
	 */
	@Override
	protected Iterator<Delivery> iterator(Delivery currentVertex, Collection<Delivery> unvisited, CityMap g, double currentTime) {
		List<Delivery> sortedUnvisited = new ArrayList<>(unvisited);
		sortedUnvisited.sort((d1, d2) -> {
			double distanceToD1 = g.getDistance(currentVertex.getIntersection(), d1.getIntersection());
			double distanceToD2 = g.getDistance(currentVertex.getIntersection(), d2.getIntersection());

			double arrivalTimeToD1 = currentTime + distanceToD1;
			double arrivalTimeToD2 = currentTime + distanceToD2;

			int timeWindowComparison = compareTimeWindows(arrivalTimeToD1, d1.getTimeWindow(), arrivalTimeToD2, d2.getTimeWindow());
			if (timeWindowComparison != 0) {
				return timeWindowComparison;
			}
			return Double.compare(distanceToD1, distanceToD2);
		});

		return new SeqIter(sortedUnvisited, currentVertex, g);
	}

	/**
	 * Compares time windows based on arrival times and window constraints
	 * @param arrivalTime1 the arrival time to the first delivery
	 * @param tw1          the time window of the first delivery
	 * @param arrivalTime2 the arrival time to the second delivery
	 * @param tw2          the time window of the second delivery
	 * @return a comparison result
	 */
	private int compareTimeWindows(double arrivalTime1, TimeWindow tw1, double arrivalTime2, TimeWindow tw2) {
		double tw1Start = convertToSecondsPastHour(8,tw1.getStartingTime());
		double tw1End = convertToSecondsPastHour(8,tw1.getEndingTime());
		double tw2Start = convertToSecondsPastHour(8,tw2.getStartingTime());
		double tw2End = convertToSecondsPastHour(8,tw2.getEndingTime());

		boolean isInWindow1 = arrivalTime1 >= tw1Start && arrivalTime1 <= tw1End;
		boolean isInWindow2 = arrivalTime2 >= tw2Start && arrivalTime2 <= tw2End;

		if (isInWindow1 && isInWindow2) {
			return Double.compare(tw1End, tw2End);
		}
		if (isInWindow1) {
			return -1;
		}
		if (isInWindow2) {
			return 1;
		}
		if (arrivalTime1 < tw1Start && arrivalTime2 < tw2Start) {
			return Double.compare(tw1Start, tw2Start);
		}
		return Double.compare(tw2End, tw1End);
	}

	/**
	 * Converts a given time to seconds past the specified hour
	 * @param hour the specified hour
	 * @param time the time to be converted
	 * @return the time in seconds past the specified hour
	 */
	private double convertToSecondsPastHour(int hour,Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		return (hours - hour) * 3600 + minutes * 60 + seconds;
	}
}
