package metrics;

/**
 * This class implements the interface IMetrics. It stores two arrays of type int.
 */
public class Specificities implements IMetrics {

    /** An array attribute that contains the predictions */
    private int[] preds;
    /** An array attribute that contains the real classes */
    private int[] classes;
    /** An attribute that stores the number of configurations of the class variable */
    private int s;

    /**
     * Specificities' contructor receives two arrays of type int and stores them in memory.
     * @param p a predictions array
     * @param c a class array
     */
    public Specificities(int[] p, int[] c) {
        for(int it : c)
            s = Math.max(s, it+1);
        preds = p;
        classes = c;
    }

    /**
     * This method computes a weighted average specitivity of the classifier.
     * For each class (in predictions) it computes the specitivity (all true negatives over all true negatives plus false positives), assuming that
     * the current one is the negative and remaining ones are positive. And, in the end
     * of the day, the method returns the weighted average of all results.
     * @return specificities the specitivity for each class plus a weighted specitivity of the predictions made by the classifier.
     */
    @Override
    public double[] metric_score() {
        /** Arrays of size s, True Positive and False Negative, that contain the countings for each class, and an array that contains the number of times each class appears in classes*/
        int[] TP = new int[s],
        FN = new int[s],
        Nc = new int[s];
        /**An array that will contain the sensitivity for each class and the weighted average */
        double[] sensitivities = new double[s+1];

        /* Sensitivity = TP / (TP + FN) */
        for(int c=0; c<s; c++){
            for(int i=0; i<preds.length; i++) {
                if(c==0) Nc[classes[i]]++;
                /* True Positives */
                if(preds[i] == classes[i] && c == preds[i])
                    TP[c]++;
                /* False Negatives */
                else if(preds[i] != classes[i] && c != preds[i])
                    FN[c]++;
            }
            /* sensitivity per class */
            sensitivities[c] = (TP[c]==0) ? 0 : (double)100*TP[c]/(TP[c] + FN[c]);
            /* Weighted average of all sensitivities */
            sensitivities[s] += sensitivities[c]*Nc[c]/classes.length;
        }
        return sensitivities;
    }

    
}