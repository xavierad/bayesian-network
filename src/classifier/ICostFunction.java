package classifier;

/**
 * ICostFunction - An interface that allows clients to implement their own Cost Function. 
 */
public interface ICostFunction {
    public float[][] computeWeights(int[][][][] N);
}