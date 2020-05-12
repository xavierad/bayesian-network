package classifier;

/**
 * MDL - This an LL extended score/cost function. This is another possible cost function to build the classifier.
 */
public class MDL extends LL {

    /** Empty constructor */
    public MDL(){
    }

    /**
     * This method will return the weight matrix using the counts and number of elements of the training dataset used, throught the Minimum Description Lenght scoring method, which is a penalized version of the Log Likelihood socring method.
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
    public double[][] computeWeights(int[][][][][] Nijkc, int N, int[][][][] NiJkc, int[][][][] NijKc
                                    , int[] Nc, int[] R_i, int S, int n) {
        // Weight Matrix allocation
        double a[][] = new double[n][n];
        // Weight matrix computation using the LL method
        a = super.computeWeights(Nijkc, N, NiJkc, NijKc, Nc, R_i, S, n);
        double s = (double) S;

        // Penalization
        for (int i_ = 0; i_ < n; i_++) {
            for (int i = 0; i < n; i++) {
                double q_i = (double) R_i[i_];
                double r_i = (double) R_i[i];
                a[i][i_] -= s*(r_i - 1.0)*(q_i - 1.0)/2 * Math.log((double) N);
            }
        }

        return a;
    }


}
