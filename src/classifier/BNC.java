
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
    private CostFunction cf;
    
    /** METHODS **/
    public BNC (/* costfunction */) {
	/* cf = costfunction; */
    }
    
    @Override
    public void build(/* Dataset */) {
	/** Structure Learning **/
	/* Process data - into Nijkc and maybe seperate Xi */
	N = countNijkc(/*Dataset*/); /* Xavier */
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
    public void predict() {
	
    }

    protected int[][][][] countNijkc( /* Dataset */){
	
    }

    /* alpha = cf.ComputeWeights(Nijkc)*/

    protected DirectedGraph getDirectedGraph(int[][] alpha){
	
    }

    protected int[][][][] computeOFE(){
	
    }

    protected int[] computeClassOFE(){
	
    }
    
}
