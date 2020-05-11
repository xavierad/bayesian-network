package classifier;

/**
 * MDL - This an LL extended score/cost function. This is another possible cost function to build the classifier.
 */
public class MDL extends LL {

    /** Empty constructor */
    public MDL(){
    }

    /** */
    @Override
    public double[][] computeWeights(int[][][][][] Nijkc, int N, int[][][][] NiJkc, int[][][][] NijKc
                                    , int[] Nc, int[] R_i, int S, int n) {
        //
        System.out.printf("MDL");
        double a[][] = new double[n][n];
        a = super.computeWeights(Nijkc, N, NiJkc, NijKc, Nc, R_i, S, n);
        double s = (double) S;

        for (int i_ = 0; i_ < n; i_++) {
            for (int i = 0; i < n; i++) {
                double q_i = (double) R_i[i_];
                double r_i = (double) R_i[i];
                a[i][i_] -= s*(r_i - 1.0)*(q_i - 1.0)/2 * Math.log((double) N);
            }
        }

        /** REMOVE */
        System.out.printf("MDL\n");
        for (int i_ = 0; i_ < n; i_++) {
            for (int i = 0; i < n; i++) {
                System.out.printf("a[%d][%d]: %f\n", i_, i, a[i_][i]);
            }
        }


        return a;
    }


}
