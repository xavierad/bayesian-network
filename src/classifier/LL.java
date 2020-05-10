package classifier;
//import java.lang.Math.*;
/**
 * LL - Log-Likelihood score/cost function. This a cost function that is offered and implemented for the classifier.
 */
public class LL implements ICostFunction {

    /** Empty constructor */
    public LL(){
    }

    /** */
    @Override
    public double[][] computeWeights(int[][][][][] Nijkc, int N, int[][][][] NiJkc, int[][][][] NijKc,
                                     int[] Nc, int[] R_i, int S, int n) {
        /* NEED EXTRA EVEN MORE IMPUTS */
        /* Nijkc (5D), N (0D), NiJkc (4D), NijKc (4D), Nc (1D)*/

        /** Array with weights - language spec inicializes all entries with 0*/
        double a[][] = new double[n][n];

        for (int i_ = 0; i_ < n; i_++) {
            for (int i = 0; i < n; i++) {
                if (i_ == i) continue;
                int q_i = R_i[i_];
                int r_i = R_i[i];
                for (int j = 0; j < q_i; j++) {
                    for (int k = 0; k < r_i; k++) {
                        for (int c = 0; c < S; c++) {
                            if (Nijkc[i_][i][j][k][c] != 0){
                                /* MAY ADD EXEPIONS OR MORE CONDITIONS */
                                double n_ijkc = (double) Nijkc[i_][i][j][k][c];
                                double n_ikc = (double) NiJkc[i_][i][k][c];
                                double n_ijc = (double) NijKc[i_][i][j][c];
                                double n_c = (double) Nc[c];

                                /* MAY CHANGE i and i_ ORDER.*/
                                a[i][i_] += (n_ijkc)/((double) N) * (Math.log(n_ijkc * n_c / (n_ikc * n_ijc)))/ Math.log(2);
                            }
                        }
                    }
                }
            }
        }

        System.out.printf("LL\n");
        for (int i_ = 0; i_ < n; i_++) {
            for (int i = 0; i < n; i++) {
                System.out.printf("a[%d][%d]: %f\n", i_, i, a[i_][i]);
            }
        }

        return a;
    }

}
