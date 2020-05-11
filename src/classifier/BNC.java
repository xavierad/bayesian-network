package classifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.*;
import data.*;

/**
 * BNC - This class implements the interface IClassifier
 * It will override built and predict methods.
 */
public class BNC implements IClassifier {

    /** The number of instances int the dataset*/
    private int N;
    /** The number of random variables in the dataset*/
    private int n;
    /** A multidimensional array that will contain Nijkc countings of each pair i-i'*/
    private int[][][][][] Nijkc;
    /** A multidimensional array that will contain NJikc cumulative countings (through j) of each pair i-i'*/
    private int[][][][] NJikc;
    /** A multidimensional array that will contain NJikc cumulative countings (through k) of each pair i-i'*/
    private int[][][][] NKijc;
    /** An array that will contain the countings that the class takes its c-th value */
    private int[] Nc;
    /** An array that will contain the number of values each feature variable takes*/
    private int[] r;
    /** An integer variable that will contain the maximum value of the class variable */
    private int s;

    /** An 2-dimensional array that will contain the weights for each pair i-i' */
    private double[][] alphas;
    /** A multidimensional array that will contain the parameters learning of a node i */
    private double[][][][] thetas;
    /** An array that will contain the parameters learning of the class */
    private double[] thetaC;

    private List<Connections> G;

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

        N = train_data.getDataSize();
        n = train_data.getRVDimension()-1;
        /* Auxiliary variables, keep the maximum values of random variables */
        r = new int[n];
        for(int i=0; i<r.length; i++)
            r[i] = train_data.getRVariable(i).getMax_value() + 1;
        s = train_data.getRVariable(n).getMax_value() + 1;

        countNijkc(train_data);

        alphas = cf.computeWeights(Nijkc, N, NJikc, NKijc, Nc, r, s, n);

        G = getDirectedGraph(alphas);

        computeOFE();

        computeClassOFE();
    }

    @Override
    public int[] predict(Dataset test_data) {
        int Nt = test_data.getDataSize();
        int nt = test_data.getRVDimension()-1;
        int[] predictions = new int[Nt];

        double[] Pc = new double[s];
        double[] Pins = new double[s];

        // to iterate over each instance
        for(int line=0; line<Nt; line++) {
            for (int c=0; c<s; c++) {
                Pins[c] = thetaC[c];

                for (Connections it : G){
                    int i = it.getSon();
                    int _i = it.getFather();

                    int k = test_data.getRVariable(i).getValue(line);
                    int j = test_data.getRVariable(_i).getValue(line);
                    Pins[c] *= thetas[i][j][k][c];
                }
            }

            // Computing the probability of each class given an instance and assigning the predicion class
            double max = 0.0;
            for (int c=0; c<s; c++) {
                Pc[c] = Pins[c] / (Arrays.stream(Pins).sum());
                if (Pc[c] > max){
                    predictions[line] = c;
                    max = Pc[c];
                }
            }
        }
        return predictions;

    }


    /**
     * This method receives an object of type Dataset and returns void
     * A multidimensional array that contains the countings Nijkc for
     * each pair parent-child _i and i, and the cumulative ones NKijc
     * and NJikc (atributes of BND class) are computed.
     * @param train_data dataset to be used to compute the countings.
     */
    protected void countNijkc(Dataset train_data){

        int max = 0;
        for(int i=0; i<n; i++)
            max = Math.max(max,train_data.getRVariable(i).getMax_value());

        Nijkc = new int[n][n][max+1][max+1][s];
        NJikc = new int[n][n][max+1][s];
        NKijc = new int[n][n][max+1][s];

        Nc = new int[s];

        /* i is the feature node that will have as parent node _i */
        for(int i=0; i<n; i++) {
            for(int _i=0; _i<n; _i++) {
                if(_i==i) continue;
                for(int line=0; line<train_data.getDataSize(); line++) {
                    int j = train_data.getRVariable(_i).getValue(line);
                    int k = train_data.getRVariable(i).getValue(line);
                    int c = train_data.getRVariable(n).getValue(line);

                    Nijkc[_i][i][j][k][c]++;
                    NJikc[_i][i][k][c]++;
                    NKijc[_i][i][j][c]++;
                    if(i == 0 && _i == 1)
                      Nc[c]++;
                }
            }
        }
    }

    /**********/
    protected List<Connections> getDirectedGraph(double[][] alpha) {
        int w = 0;
        int k = 0;
        double maximumWeight = 0;
        List<Integer> visitedNodes = new ArrayList<>();
        List<Connections> newList = new ArrayList<Connections>();
        visitedNodes.add(0);
        newList.add(new Connections(0, 0));
        while (visitedNodes.size() != alpha.length) {
            maximumWeight = Double.NEGATIVE_INFINITY;
            for (int i : visitedNodes) {
                for (int j = 0; j < alpha.length; j++) {
                    if (maximumWeight < alpha[i][j] && !visitedNodes.contains(j)) {
                        maximumWeight = alpha[i][j];
                        k = i;
                        w = j;
                    }
                }
            }
            visitedNodes.add(w);
            newList.add(new Connections(k, w));
        }

        return newList;
    }

    //mudar depois os parâmetros
    /**
     * This method will receive the multidimensinal array Nijkc and the
     * directed graph G in order to compute the observed frequency
     * estimates (OFE).
     * @param N
     * @param G
     */
    protected void computeOFE(){
        /* As N contains the counting for all possible parent-child configurations
        it is needed to use G to know those chosen directions */
        int max = 0;
        for(int i : r)
            max = Math.max(max,i);

        thetas = new double[n][max][max][s];

        // from the feature root, go to the childs
        //FALTA: QUANDO SOUBER A ESTRUTURA DE G, IR DE PAI EM PAI (PARA DEPOIS ACEDER EM _i e saver qi e ri)
        //Node it = G.getFirst();
        for (Connections it : G){
            int _i = it.getFather();
            int i = it.getSon();
            for(int j=0; j < r[_i]; j++)
                for(int k=0; k < r[i]; k++)
                    for(int c=0; c < s; c++)
                        thetas[i][j][k][c] =
                        (Nijkc[_i][i][j][k][c] + 0.5) / (NKijc[_i][i][j][c] + r[i]*0.5);
        }
    }

    /**
     * This method will compute the observed frequency estimates
     * of the variable class.
     */
    protected void computeClassOFE(){
        thetaC = new double[s];

        for(int c=0; c < s; c++)
            thetaC[c] = (Nc[c] + 0.5) / (N + s*0.5);
    }

}
