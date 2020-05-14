package classifier;

/**
 * ICostFunction - An interface that allows clients to implement their own Score Function.
 */
public interface IScoreFunction {

    /**
     * Method which computes weights according to various counts of the training dataset.
     * @param Nijkc Multidimensional array that contains number of instances in the data T where the variable Xi takes its k-th value xi_k, the variables in Xi' take their j-th configuration wij , and the class variable C its c-th value.
     * @param N Number of intances counted
     * @param NiJkc Multidimensional array that contains Nijkc cumulative countings (through j) of each pair i-i'
     * @param NijKc Multidimensional array that contains Nijkc cumulative countings (through k) of each pair i-i'
     * @param Nc Array that contains the countings that the class takes its c-th value
     * @param R_i Array that contains the number of configurations of each feature variable
     * @param S Number of configurations of the class variable
     * @param n Number of random variables of the training dataset used
     * @return Weight matrix
    */
    public double[][] computeWeights(int[][][][][] Nijkc, int N, int[][][][] NiJkc, int[][][][] NijKc
                                    , int[] Nc, int[] R_i, int S, int n);
}
