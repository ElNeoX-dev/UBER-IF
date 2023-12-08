package com.malveillance.uberif.model.algo;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Delivery;

import java.util.Collection;
import java.util.Iterator;

public class TSP1 extends TemplateTSP {
	@Override
	protected int bound(Delivery currentVertex, Collection<Delivery> unvisited) {
		return 0;
	}

	@Override
	protected Iterator<Delivery> iterator(Delivery currentVertex, Collection<Delivery> unvisited, CityMap g) {
		return new SeqIter(unvisited, currentVertex, g);
	}
}
