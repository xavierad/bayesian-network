package classifier;

import java.util.ArrayList;
import java.util.List;

public class Graph {

	public void graph() {
		int[][] alpha = { { 0, 7, 0, 5, 0, 0, 0 }, { 10, 0, 8, 9, 7, 0, 0 }, { 0, 8, 0, 0, 5, 0, 0 },
				{ 5, 9, 0, 0, 15, 6, 0 }, { 0, 7, 5, 15, 0, 8, 9 }, { 0, 0, 0, 6, 8, 0, 11 },
				{ 0, 0, 0, 0, 9, 11, 0 } };
	    int mstWeight = 0;
		int w = 0;
		int maximumWeight = 0;
		List<Integer> visitedNodes = new ArrayList<>();
		visitedNodes.add(0);
		while (visitedNodes.size() != alpha.length) {
			maximumWeight = 0;
			for (int i : visitedNodes) {
				for (int j = 0; j < alpha.length; ++j) {
					if (maximumWeight < alpha[i][j] && !visitedNodes.contains(j)) {
						System.out.format("i: %d j: %d with weight %2d\n", i, j, alpha[i][j]);
						maximumWeight = alpha[i][j];
						w = j;
					}

				}

			}
			System.out.format("added %d with weight %d\n", w, maximumWeight);
			visitedNodes.add(w);
			mstWeight += maximumWeight;
		}

		System.out.printf("MST WEIGHT = %d \n", mstWeight);
		System.out.printf("MST = %s\n", visitedNodes.toString());

		for (int i = 0; i < alpha.length; i++) {
			for (int j = 0; j < alpha[i].length; j++) {
				System.out.format("%2d ", alpha[i][j]);
			}
			System.out.println();
		}
	}

}
