package classifier;

import java.util.ArrayList;
import java.util.List;

/**
 * BNC - This class implements the interface IClassifier
 * It will override built and predict methods. 
 */
public class BNC implements IClassifier {
    
    /** A multidimensional array that will contain Nijkc countings of each pair i-i'*/
    private int[][][][][] Nijkc;
    /** A multidimensional array that will contain NJikc cumulative countings (through j) of each pair i-i'*/
    private int[][][][] NJikc;
    /** A multidimensional array that will contain NJikc cumulative countings (through k) of each pair i-i'*/
    private int[][][][] NKijc;    
    /** An array that will contain the maximum values of each feature variable */
    private int[] r;
    /** An integer variable that will contain the maximum value of the class variable */
    private int s;

    /** An 2-dimensional array that will contain the weights for each pair i-i' */
    private float[][] alphas;    
    /** A multidimensional array that will contain the parameters learning of a node i */
    private float[][][][] thetas;
     /** An array that will contain the parameters learning of the class */
    private float[] thetaC;

    private String G;

    private ICostFunction cf;
    

    /**
     * The BNC's constuctor will receive a 
     * cost function and assigns it to cf atribute.
     * @param costfuntion cost function that computes the weights.
     */
    public BNC (ICostFunction costfuntion) {
        cf = costfuntion;
    }
    
    /**
     * This method provides a TAN classifier built. It computes the necessary
     * countings, the alphas weights, the directed tree and the parameters learning.
     * @param train_data dataset to be used to build the classifier.
     */
    @Override
    public void build(Dataset train_data) {

        /* Auxiliary variables, keep the maximum values of random variables */ 
        r = new int[train_data.getRVDimension()-1];
        for(int i=0; i<r.length; i++)
            r[i] = train_data.random_vector[i].max_value;
        s = train_data.random_vector[train_data.getRVDimension()-1].max_value;

        /** Structure Learning **/
        /* Process data - into Nijkc and maybe seperate Xi */
        countNijkc(train_data); /* Xavier */

        /* Get Weigths - acoording to the model chosen */
        alphas = cf.computeWeights(Nijkc,r,s); /* Vicente */

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


    /**
     * This method receives an object of type Dataset and returns void
     * A multidimensional array that contains the countings Nijkc for 
     * each pair parent-child _i and i, and the cumulative ones NKijc
     * and NJikc (atributes of BND class) are computed. 
     * @param train_data dataset to be used to compute the countings.
     */
    protected void countNijkc(Dataset train_data){

        
        RVariable[] rvector = train_data.random_vector;
        int n = train_data.getRVDimension()-1;

        int max = 0;
        for(RVariable rvar:rvector) 
            max = Math.max(max,rvar.max_value); 

        Nijkc = new int[n][n][max+1][max+1][max+1];
        NJikc = new int[n][n][max+1][max+1];
        NKijc = new int[n][n][max+1][max+1];
        
        /* i is the feature node that will have as parent node _i */
        for(int i=0; i<n; i++) {    
            for(int _i=0; _i<n; _i++) {
                if(_i==i) continue;
                for(int line=0; line<train_data.getDataSize(); line++) {                    
                    int j = rvector[_i].values.get(line);
                    int k = rvector[i].values.get(line);
                    int c = rvector[n].values.get(line);
                    
                    Nijkc[_i][i][j][k][c]++;
                    NJikc[_i][i][k][c]++;
                    NKijc[_i][i][j][c]++;
                }
            }           
        }
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
            for(int j=0; j<r[_i]; j++)
                for(int k=0; k<r[i]; k++)
                    for(int c=0; c<s; c++)
                        thetas[i][j][k][c] = (N[_i][i][j][k][c] + 0.5) / (NKijc[_i][i][j][c] + r[i]*0.5);
    }

    protected int[] computeClassOFE(){
	
    }
    
}
