package classifier;

/**
 * BNC
 */
public class BNC implements IClassifier {
    /** ATRIBUTES **/
    private int[][][][][] N;
    private float[][] alphas;
    private String G;
    private float[][][][] thetas;
    private float[] thetaC;
    private ICostFunction cf;
    
    /** METHODS **/
    public BNC (ICostFunction costfuntion) {
        cf = costfuntion;
    }
    
    @Override
    public void build(Dataset train_data) {
        /** Structure Learning **/
        /* Process data - into Nijkc and maybe seperate Xi */
        N = countNijkc(train_data); /* Xavier */

        /* Get Weigths - acoording to the model chosen */
        alphas = cf.computeWeights(N); /* Vicente */

        /* Build Weighted Graph */
        
        /* Transform to MaxSpanningTree (Undirected) */
        
        /* Transform to Directed Tree - arbitearly chosen root */
        G = getDirectedGraph(alphas); /* Afonso */

        /* (?) Add leafs to tree with C values */

        /** Parameter Learning (or just BNC) **/
        /* Get OFE */
        thetas = computeOFE(N,G);

        /* Get ClassOFE */
        thetaC = computeClassOFE(N,G);
    }
    
    @Override
    public void predict(Dataset test_data) {
	
    }

    protected int[][][][][] countNijkc(Dataset train_data){
        RVariable[] rvector = train_data.random_vector;
        int n = train_data.getRVDimension()-1;

        int max = 0;
        for(RVariable rvar:rvector) 
            max = Math.max(max,rvar.max_value);

        int N[][][][][] = new int[n][n][max+1][max+1][max+1];
        
        /* i is the feature node that will have as parent node _i */
        for(int i=0; i<n; i++) {    
            for(int _i=0; _i<n; _i++) {
                if(_i==i) continue;
                for(int line=0; line<train_data.getDataSize(); line++) {                    
                    int j = rvector[_i].values.get(line);
                    int k = rvector[i].values.get(line);
                    int c = rvector[n].values.get(line);
                    N[_i][i][j][k][c]++;
                }
            }           
        }

        return N;
    }

    protected String getDirectedGraph(int[][] alpha) {

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
	return visitedNodes.toString();
    }

    protected int[][][][] computeOFE(){
	
    }

    protected int[] computeClassOFE(){
	
    }
    
}
