package metrics;

/**
 * This class implements the interface IMetrics. It stores two arrays of type int.
 */
public class Accuracy implements IMetrics {
    /** */
    private double[] score;

    /**
     * The Accuracy's contructor receives two arrays of type int and stores them in memory.
     * @param preds a predictions array.
     * @param classes a class array.
     */
    public Accuracy(int[] preds, int[] classes) {
        /** The number of correct predictions */
        int ncp=0;

        for (int i=0; i<preds.length; i++)
            if(preds[i] == classes[i])
                ncp++;
        score = new double[1];
        score[0] = (double)ncp/preds.length;
    }

    /**
     * This method return the accuracy, the proportion of correct predictions.
     * @return res a 0-dimension array that contains the accuracy computed.
     */
    @Override
    public double[] metric_score() {
        return score;
    }

    /**
     * This method will print the resume of the classifier metrics.
     */
    @Override
    public String toString() {
        return String.format("%.2f%%", score[0]*100);
    }
}
