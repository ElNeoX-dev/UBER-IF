package com.malveillance.uberif.model.algo;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Delivery;

import java.util.Collection;
import java.util.Iterator;

public class TSP1 extends TemplateTSP {
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

	@Override
	protected Iterator<Delivery> iterator(Delivery currentVertex, Collection<Delivery> unvisited, CityMap g) {
		return new SeqIter(unvisited, currentVertex, g);
	}
}
