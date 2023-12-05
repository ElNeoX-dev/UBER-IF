package com.malveillance.uberif.model.algo;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Delivery;
import com.malveillance.uberif.model.Intersection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SeqIter implements Iterator<Delivery> {
	private ArrayList<Delivery> candidates;
	private int currentIndex;

	/**
	 * Create an iterator to traverse the set of vertices in <code>unvisited</code>
	 * which are successors of <code>currentVertex</code> in <code>g</code>
	 * Vertices are traversed in the same order as in <code>unvisited</code>
	 * @param unvisited
	 * @param currentVertex
	 * @param c
	 */
	public SeqIter(Collection<Delivery> unvisited, Delivery currentVertex, CityMap c) {
		this.candidates = new ArrayList<>();
		for (Delivery tempDelivery : unvisited) {
			if (c.hasRoadSegment(currentVertex.getIntersection(), tempDelivery.getIntersection())) {
				candidates.add(tempDelivery);
			}
		}
		this.currentIndex = candidates.size() - 1;
	}

	@Override
	public boolean hasNext() {
		return currentIndex >= 0;
	}

	@Override
	public Delivery next() {
		return candidates.get(currentIndex--);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove operation is not supported");
	}
}
