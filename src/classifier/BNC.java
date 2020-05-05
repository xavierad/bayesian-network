package classifier;

/**
 * BNC
 */
public class BNC implements IClassifier {
    /** ATRIBUTES **/
    private int[][][][][] N;
    private float[][] alpha;
    private DirectedGraph G;
    private float[][][][] theta;
    private float[] thetaC;
    private ICostFunction cf;
    
    /** METHODS **/
    public BNC (/* costfunction */) {
	/* cf = costfunction; */
    }
    
    @Override
    public void build(Dataset train_data) {
        /** Structure Learning **/
        /* Process data - into Nijkc and maybe seperate Xi */
        N = countNijkc(train_data); /* Xavier */

        /* Get Weigths - acoording to the model chosen */
        alpha = cf.computeWeights(N); /* Vicente */

        /* Build Weighted Graph */
        
        /* Transform to MaxSpanningTree (Undirected) */
        
        /* Transform to Directed Tree - arbitearly chosen root */
        G = getDirectedGraph(alpha); /* Afonso */

        /* (?) Add leafs to tree with C values */

        /** Parameter Learning (or just BNC) **/
        /* Get OFE */
        theta = computeOFE(N,G);

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
        for(RVariable rvar:rvector) max = Math.max(max,rvar.max_value);

        int N[][][][][] = new int[n][n][max][max][max];
        
        /* 
        i is in range [0;n-2[ because the last feature (in position n-1) cannot be its own parent
        _i is in range [i+1;n-1[ because it is not need to count for parents that were childs.

        For example: X1|X2|X3 --> Then i = {1,2} (childs) and _i = {2,3} (parents), like a distributive parent assignement
        */
      /*  for(int i=0; i<n-2; i++) {    
            for(int _i=i+1; _i<n-1; _i++) {
                for(int line=0; line<train_data.getDataSize(); line++) {                    
                    int j = rvector[_i].values.get(line);
                    int k = rvector[i].values.get(line);
                    int c = rvector[n+1].values.get(line);
                    N[_i][i][j][k][c]++;
                }
            }           
        }*/

        /* i is the feature node that will have as parent node _i */
        for(int i=0; i<n; i++) {    
            for(int _i=0; _i<n; _i++) {
                if(_i==i) continue;
                for(int line=0; line<train_data.getDataSize(); line++) {                    
                    int j = rvector[_i].values.get(line);
                    int k = rvector[i].values.get(line);
                    int c = rvector[n+1].values.get(line);
                    N[_i][i][j][k][c]++;
                }
            }           
        }

        return N;
    }

    /* alpha = cf.ComputeWeights(Nijkc)*/

    protected DirectedGraph getDirectedGraph(int[][] alpha){
	
    }

    protected int[][][][] computeOFE(){
	
    }

    protected int[] computeClassOFE(){
	
    }
    
}
