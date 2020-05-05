
/**
 * BNC
 */
public class BNC implements IClassifier {
    /** ATRIBUTES **/
    private int[][][][] N;
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

    protected int[][][][] countNijkc(Dataset train_data){
        int n = train_data.getRVDimension()-1, qi, ri, s;
        int weights[][] = new int[n][n];
        int N[][][][];

        for(int i=0; i<n-1; i++) {
            for(int _i=i+1; i<n; i++) {
                qi=train_data.random_vector[i+1].max_value;;

                for(int j=1; j<=qi ; j++) {                
                    ri=train_data.random_vector[i].max_value;

                    for(int k=1; k<=ri; k++) {                
                        s=train_data.random_vector[n+1].max_value;

                        for(int c=1; c<=s; c++) {
                            weights[i][_i] = 1;
                        }
                    }
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
