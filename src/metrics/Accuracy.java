package metrics;

/**
 * This class implements the interface IMetrics. It stores two arrays of type int.
 */
public class Accuracy implements IMetrics {

    /** Accuracy measurament */
    private double[] score;

    /**
     * The Accuracy's contructor receives two arrays of type int and computes the accuracy according to these.
     * @param preds predictions array.
     * @param classes test dataset class array.
     */
    public Accuracy(int[] preds, int[] classes) {
        //The number of correct predictions
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
     * This method will return a string with the accuracy score as a percentage.
     */
    @Override
    public String toString() {
        return String.format("%.2f%%", score[0]*100);
    }
}
