package classifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.*;
import data.*;

/**
 * TAN - This class implements the interface IClassifier
 * It will override built and predict methods.
 */
public class TAN implements IClassifier {

    private String[] feature_names;

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
    /** An array that will store the number of configurations of each feature variable */
    private int[] r;
    /** An attribute that will store the number of configurations of the class variable */
    private int s;

    /** An 2-dimensional array that will contain the weights for each pair i-i' */
    private double[][] alphas;
    /** A multidimensional array that will contain the parameters learning of a node i */
    private double[][][][] thetas;
    /** An array that will contain the parameters learning of the class */
    private double[] thetaC;


    private List<Connections<Integer>> G;



    private IScoreFunction cf;


    /**
     * The TAN's constuctor will receive a
     * scorefunction and assigns it to cf atribute.
     * @param scorefuntion score function that computes the weights.
     */
    public TAN (IScoreFunction scorefuntion) {
        cf = scorefuntion;
    }

    /**
     * This method provides a TAN classifier built. It computes the necessary
     * countings, the alphas weights, the directed tree and the parameters learning.
     * @param train_data dataset to be used to build the classifier.
     */
    @Override
    public void build(Dataset train_data) {

        // Getting the data size and the number of features
        N = train_data.getDataSize();
        n = train_data.getRVDimension()-1;

        // To store the features names
        feature_names = new String[n];

        // To store the maximum values of each feature variable and thier names
        r = new int[n];

        for(int i=0; i<r.length; i++) {
            feature_names[i] = train_data.getRVariable(i).getRVName();
            r[i] = train_data.getRVariable(i).getMax_value() + 1;
        }
        s = train_data.getRVariable(n).getMax_value() + 1;

        // Computations of Nijkc, NJikc, NKijc and Nc
        countNijkc(train_data);

        // Computations of alphas
        alphas = cf.computeWeights(Nijkc, N, NJikc, NKijc, Nc, r, s, n);
        
        // Construction of the directed tree
        G = getMaxSpanTreeConnections(alphas);

        // Computations of the parameters learning
        computeOFE();
        computeClassOFE();
    }

    /**
     * This method provides an array of predictions that will make use of the parameters, tree built and the test data
     * @param test_data data to predict
     * @return predictions and array with predictions for each instance
     */
    @Override
    public int[] predict(Dataset test_data) {
        int Nt = test_data.getDataSize();
        int[] predictions = new int[Nt];

        double[] Pc = new double[s];
        double[] Pinst = new double[s];

        // To iterate over each instance
        for(int line=0; line<Nt; line++) {
            for (int c=0; c<s; c++) {
                Pinst[c] = Math.log(thetaC[c]);

                // To iterate over each connection and get son and father
                for (Connections<Integer> it : G){
                    int i = it.getSon();
                    int _i = it.getFather();

                    int k = test_data.getRVariable(i).getValue(line);
                    int j = test_data.getRVariable(_i).getValue(line);

                    // Computation of each instance in test_data
                    Pinst[c] += Math.log(thetas[i][j][k][c]);
                }
            }

            // Computing the probability of each class given an instance and assigning the predicion class
            double max = Double.NEGATIVE_INFINITY;
            for (int c=0; c<s; c++) {
                Pc[c] = Pinst[c] - (Arrays.stream(Pinst).sum());
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

        // In order to know the maximum dimensions of Nijkc, NJikc and NKijc,
        // it is needed to know the maximum of maximum values of each feature
        int max = 0;
        for(int i=0; i<n; i++)
            max = Math.max(max,train_data.getRVariable(i).getMax_value());

        Nijkc = new int[n][n][max+1][max+1][s];
        NJikc = new int[n][n][max+1][s];
        NKijc = new int[n][n][max+1][s];

        Nc = new int[s];

        // i is the feature node that will have as parent node _i
        for(int i=0; i<n; i++) {
            for(int _i=0; _i<n; _i++) {
                for(int line=0; line<train_data.getDataSize(); line++) {
                    int j = train_data.getRVariable(_i).getValue(line);
                    int k = train_data.getRVariable(i).getValue(line);
                    int c = train_data.getRVariable(n).getValue(line);

                    // For each i and _i, given j, k and c increments in
                    // each multidimension array in the correspondent position
                    NJikc[_i][i][k][c]++;
                    NKijc[_i][i][j][c]++;
                    if (i == _i){
                        Nijkc[_i][i][0][k][c] = NJikc[_i][i][k][c];
                    } else {
                        Nijkc[_i][i][j][k][c]++;
                    }

                    // Only once is needed to increment
                    if(i == 0 && _i == 1)
                      Nc[c]++;
                }
            }
        }
    }

    /**
     * This method receives an adjacency matrix with weights between
     * nodes and returns a list of the connections between nodes.
     * The goal is to perform the Prim algorithm to obtain the max spanning tree
     * @param alpha alpha is an adjacency matrix
     * @return List of connections
     */
    protected List<Connections<Integer>> getMaxSpanTreeConnections(double[][] alpha) {

        int w = 0;
        int k = 0;
        double maximumWeight = 0;

        List<Integer> visitedNodes = new ArrayList<>();
        List<Connections<Integer>> maxST = new ArrayList<Connections<Integer>>();

        // Choosing node 1 by default as root of the max spanning tree
        visitedNodes.add(0);
        maxST.add(new Connections<Integer>(0, 0));

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
            maxST.add(new Connections<Integer>(w, k));
        }
        return maxST;
    }

    /**
     * This method will make use of the multidimensinal array Nijkc and the
     * directed graph G in order to compute the observed frequency
     * estimates (OFE).
     */
    protected void computeOFE(){
        // Since Nijkc contains the counting for all possible parent-child configurations
        // it is needed to use G to know those chosen directions
        int max = 0;
        for(int i : r)
            max = Math.max(max,i);

        thetas = new double[n][max][max][s];

        // For each connection, get the son and the father
        for (Connections<Integer> it : G){
            int _i = it.getFather();
            int i = it.getSon();
            for(int j=0; j < r[_i]; j++)
                for(int k=0; k < r[i]; k++)
                    for(int c=0; c < s; c++){
                        //thetas[i][j][k][c] = (Nijkc[_i][i][j][k][c] + 0.5) / (NKijc[_i][i][j][c] + r[i]*0.5);
                        if( i != _i ){
                            thetas[i][j][k][c] = (Nijkc[_i][i][j][k][c] + 0.5) / (NKijc[_i][i][j][c] + r[i]*0.5);
                        } else {
                            thetas[i][j][k][c] = (NJikc[_i][i][k][c] + 0.5) / (NKijc[_i][i][0][c] + r[i]*0.5);
                        }
                    }
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

    protected String connectiontoString(Connections<Integer> it) {

        if(it.getSon() == it.getFather())
            return feature_names[it.getSon()] + " : ";
        return feature_names[it.getSon()] + " : " + feature_names[it.getFather()];
    }
    /*
    public List<String> structuretoString() {
        List<String> str = new ArrayList<String>();
        for (Connections it : G) {
            str.add((connectiontoString(it)));
        }
        return str;
    }*/

    @Override
    public String toString() {
        int i=0;
        String str="";
        for (Connections<Integer> it : G) {
            if(i==0) str += String.format("%-20s%s\n", ("Classifier:"), (connectiontoString(it)));
            else str += String.format("%-20s%s\n",("           "), (connectiontoString(it)));
            i++;
        }
        return str;
    }



}
