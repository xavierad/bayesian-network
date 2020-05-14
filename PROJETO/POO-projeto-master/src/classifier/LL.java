package classifier;
//import java.lang.Math.*;
/**
 * LL - Log-Likelihood score/cost function. This a cost function that is offered and implemented for the classifier.
 */
public class LL implements IScoreFunction {

    /** Empty constructor */
    public LL(){
    }

    /**
     * This method will return the weight matrix using the counts and number of elements of the training dataset used, through the log-likelihood scoring ctriteria.
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
    @Override
    public double[][] computeWeights(int[][][][][] Nijkc, int N, int[][][][] NiJkc, int[][][][] NijKc,
                                     int[] Nc, int[] R_i, int S, int n) {
        // Weight Matrix allocation
        double a[][] = new double[n][n];

        // Computation of each weight
        for (int i_ = 0; i_ < n; i_++) {
            for (int i = 0; i < n; i++) {
                if (i_ == i) continue;
                int q_i = R_i[i_];
                int r_i = R_i[i];
                for (int j = 0; j < q_i; j++) {
                    for (int k = 0; k < r_i; k++) {
                        for (int c = 0; c < S; c++) {
                            if (Nijkc[i_][i][j][k][c] != 0 && Nc[c] != 0){
                                double n_ijkc = (double) Nijkc[i_][i][j][k][c];
                                double n_ikc = (double) NiJkc[i_][i][k][c];
                                double n_ijc = (double) NijKc[i_][i][j][c];
                                double n_c = (double) Nc[c];

                                a[i][i_] += (n_ijkc)/((double) N) * (Math.log(n_ijkc * n_c / (n_ikc * n_ijc)))/ Math.log(2);
                            }
                        }
                    }
                }
            }
        }

        return a;
    }

}
