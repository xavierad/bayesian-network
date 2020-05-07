package classifier;

/**
 * BNC
 */
public class BNC implements IClassifier {
    /** ATRIBUTES **/
    private int[][][][][] Nijkc;
    private int[][][][] NJikc;
    private int[][][][] NKijc;
    
    private float[][] alphas;    
    private float[][][][] thetas;
    private float[] thetaC;

    private DirectedGraph G;
    private ICostFunction cf;
    
    /** METHODS **/
    public BNC (ICostFunction costfuntion) {
        cf = costfuntion;
    }
    
    @Override
    public void build(Dataset train_data) {

        /** Structure Learning **/
        /* Process data - into Nijkc and maybe seperate Xi */
        countNijkc(train_data); /* Xavier */

        /* Get Weigths - acoording to the model chosen */
        alphas = cf.computeWeights(Nijkc); /* Vicente */

        /* Build Weighted Graph */
        
        /* Transform to MaxSpanningTree (Undirected) */
        
        /* Transform to Directed Tree - arbitearly chosen root */
        G = getDirectedGraph(alphas); /* Afonso */

        /* (?) Add leafs to tree with C values */

        /** Parameter Learning (or just BNC) **/
        /* Get OFE */
        thetas = computeOFE(Nijkc,NKijc,G);

        /* Get ClassOFE */
        thetaC = computeClassOFE(N,G);
    }
    
    @Override
    public void predict(Dataset test_data) {
	
    }

    /** countNijkc()
     * This method returns a multidimensional array that contains 
     * the countings Nijkc for each pair parent-child _i and i.
     */
    protected void countNijkc(Dataset train_data){
        RVariable[] rvector = train_data.random_vector;
        int n = train_data.getRVDimension()-1;

        int max = 0;
        for(RVariable rvar:rvector) 
            max = Math.max(max,rvar.max_value); 

        Nijkc = new int[n][n][max+1][max+1][max+1];
        
        /* i is the feature node that will have as parent node _i */
        for(int i=0; i<n; i++) {    
            for(int _i=0; _i<n; _i++) {
                if(_i==i) continue;
                for(int line=0; line<train_data.getDataSize(); line++) {                    
                    int j = rvector[_i].values.get(line);
                    int k = rvector[i].values.get(line);
                    int c = rvector[n].values.get(line);
                    Nijkc[_i][i][j][k][c]++;
                    for(int _j=0; _j<max; _j++)
                        NJikc[_i][i][k][c] += Nijkc[_i][i][_j][k][c];
                }
            }           
        }
    }

   /* protected int[][][][] countNJikc(int[][][][][]Nijkc) {
        int n=train_data.getRVDimension();
        int length = Nijkc[0][0].length;
        int N[][][][] = new int[n][length][length][length];

        for(int i=0; i<n; i++) {
            int ri = train_data.random_vector[i].max_value;
            for(int j=0; j<ri; j++) {
                NJikc[][][][]
            }
        }

    }*/

    protected DirectedGraph getDirectedGraph(int[][] alpha){
	
    }


    /** computeOFE()
     * This method will receive the multidimensinal array N and the
     * directed graph G in order to compute the observed frequency
     * estimates (OFE).
     */
    protected int[][][][] computeOFE(int[][][][][]N, DirectedGraph G){
        int n = N[0].length;

        /* As N contains the counting for all possible parent-child configurations
        it is needed to use G to know those chosen directions */       
        
        // from the feature root, go to the childs 
        //FALTA: QUANDO SOUBER A ESTRUTURA DE G, IR DE PAI EM PAI (PARA DEPOIS ACEDER EM _i e saver qi e ri)
        for(int i=0; i<n; i++)
            for(int j=0; j<qi; j++)
                for(int k=0; k<ri; k++)
                    for(int c=0; c<s; c++)
                        thetas[i][j][k][c] = (N[_i][i][j][k][c] + 0.5) / (NKijc + ri*0.5);
    }

    protected int[] computeClassOFE(){
	
    }
    
}
