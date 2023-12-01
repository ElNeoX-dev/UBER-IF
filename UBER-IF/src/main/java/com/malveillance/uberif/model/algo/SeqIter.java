package com.malveillance.uberif.model.algo;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Intersection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SeqIter implements Iterator<Intersection> {
	private ArrayList<Intersection> candidates;
	private int currentIndex;

	/**
	 * Create an iterator to traverse the set of vertices in <code>unvisited</code>
	 * which are successors of <code>currentVertex</code> in <code>g</code>
	 * Vertices are traversed in the same order as in <code>unvisited</code>
	 * @param unvisited
	 * @param currentVertex
	 * @param c
	 */
	public SeqIter(Collection<Intersection> unvisited, Intersection currentVertex, CityMap c) {
		this.candidates = new ArrayList<>();
		for (Intersection s : unvisited) {
			if (c.hasRoadSegment(currentVertex, s)) {
				candidates.add(s);
			}
		}
		this.currentIndex = candidates.size() - 1;
	}

	@Override
	public boolean hasNext() {
		return currentIndex >= 0;
	}

	@Override
	public Intersection next() {
		return candidates.get(currentIndex--);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove operation is not supported");
	}
}
