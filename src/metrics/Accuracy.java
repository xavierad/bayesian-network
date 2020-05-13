package metrics;

/**
 * This class implements the interface IMetrics. It stores two arrays of type int.
 */
public class Accuracy implements IMetrics {


    /** An array attribute that contains the predictions */
    private int[] preds;
    /** An array attribute that contains the real classes */
    private int[] classes;

    /**
     * The Accuracy's contructor receives two arrays of type int and stores them in memory.
     * @param p a predictions array.
     * @param c a class array.
     */
    public Accuracy(int[] p, int[] c) {
        preds = p;
        classes = c;
    }

    /**
     * This method computes the accuracy, the proportion of correct predictions, making a comparison with given classes, over all predictions.
     * @return res a 0-dimension array that contains the accuracy computed.
     */
    @Override
    public double[] metric_score() {
        
        /** The number of correct predictions */
        int ncp=0;

        for (int i=0; i<preds.length; i++)
            if(preds[i] == classes[i])
                ncp++;
        double[]res = new double [1];
        res[0] = (double)100*ncp/preds.length;
        return res;
    }    
}