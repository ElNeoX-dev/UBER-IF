package com.malveillance.uberif.model.algo;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Intersection;

import java.util.Collection;
import java.util.Iterator;

public class TSP1 extends TemplateTSP {
	@Override
	protected int bound(Intersection currentVertex, Collection<Intersection> unvisited) {
		return 0;
	}

	@Override
	protected Iterator<Intersection> iterator(Intersection currentVertex, Collection<Intersection> unvisited, CityMap g) {
		return new SeqIter(unvisited, currentVertex, g);
	}
}
