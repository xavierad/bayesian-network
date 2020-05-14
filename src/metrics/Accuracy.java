package metrics;

/**
 * This class implements the interface IMetrics. It stores two arrays of type int.
 */
public class Accuracy implements IMetrics {
    /** An array that contains the average score */
    private double[] score;

    /**
     * Accuracy' contructor: computes the accuracy for the whole input dataset.
     * The accuracy metric is only computed on the constructor and the score/result is saved for quick and easy acesss.
     * @param preds a predictions array
     * @param classes a class array
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
     * This method returns the accuracy, the proportion of correct predictions.
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
        return "Accuracy: "  + String.format("%.2f%%", score[0]*100);
    }
}
