package classifier;

/**
 * ICostFunction - An interface that allows clients to implement their own Cost Function.
 */
public interface ICostFunction {

    /** */
    public double[][] computeWeights(int[][][][][] Nijkc, int N, int[][][][] NiJkc, int[][][][] NijKc
                                    , int[] Nc, int[] R_i, int S, int n);
}
