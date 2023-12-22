package com.malveillance.uberif.model.algo;

/**
 * The {@code CompleteGraph} class represents a complete directed graph, where each edge has a weight within the specified range.
 */
public class CompleteGraph implements Graph {
	/**
	 * The maximum cost of an edge
	 */
	private static final int MAX_COST = 40;
	/**
	 * The minimum cost of an edge
	 */
	private static final int MIN_COST = 10;
	/**
	 * The number of vertices in the graph
	 */
	int nbVertices;
	/**
	 * The cost of the directed edge from vertex i to vertex j
	 */
	int[][] cost;

	/**
	 * Creates a complete directed graph with the specified number of vertices, and each edge having a weight within the range [MIN_COST, MAX_COST]
	 * @param nbVertices the number of vertices in the graph
	 */
	public CompleteGraph(int nbVertices) {
		this.nbVertices = nbVertices;
		int iseed = 1;
		cost = new int[nbVertices][nbVertices];
		for (int i = 0; i < nbVertices; i++) {
			for (int j = 0; j < nbVertices; j++) {
				if (i == j) cost[i][j] = -1;
				else {
					int it = 16807 * (iseed % 127773) - 2836 * (iseed / 127773);
					if (it > 0) iseed = it;
					else iseed = 2147483647 + it;
					cost[i][j] = MIN_COST + iseed % (MAX_COST - MIN_COST + 1);
				}
			}
		}
	}

	/**
	 * Gets the number of vertices in the graph
	 * @return the number of vertices
	 */
	@Override
	public int getNbVertices() {
		return nbVertices;
	}

	/**
	 * Gets the cost of the directed edge from vertex i to vertex j
	 * @param i the source vertex
	 * @param j the destination vertex
	 * @return the cost of the edge, or -1 if the indices are out of bounds
	 */
	@Override
	public int getCost(int i, int j) {
		if (i < 0 || i >= nbVertices || j < 0 || j >= nbVertices)
			return -1;
		return cost[i][j];
	}

	/**
	 * Checks if there is a directed arc from vertex i to vertex j
	 * @param i the source vertex
	 * @param j the destination vertex
	 * @return true if there is an arc, false otherwise
	 */
	@Override
	public boolean isArc(int i, int j) {
		if (i < 0 || i >= nbVertices || j < 0 || j >= nbVertices)
			return false;
		return i != j;
	}
}
