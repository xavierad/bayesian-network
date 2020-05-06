package classifier;

/**
 * BNC
 */
public class BNC implements IClassifier {
    /** ATRIBUTES **/
    private int[][][][][] N;
    private float[][] alphas;
    private DirectedGraph G;
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

    protected DirectedGraph getDirectedGraph(int[][] alpha){
	
    }

    protected int[][][][] computeOFE(){
	
    }

    protected int[] computeClassOFE(){
	
    }
    
}
