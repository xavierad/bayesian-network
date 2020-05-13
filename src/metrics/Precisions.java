package metrics;

/**
 * This class implements the interface IMetrics. It stores two arrays of type int.
 */
public class Precisions implements IMetrics {

    int[] preds;
    int[] classes;
    /** An attribute that will store the number of configurations of the class variable */
    int s;

    public Precisions(int[] p, int[] c) {
        for(int it : c)
            s = Math.max(s, it+1);
        preds = p;
        classes = c;
    }

    /**
     * This method computes a weighted average precision of the classifier.
     * For each class (in predictions) it computes the precision (all true positives over predicted positives), assuming that
     * the current one is the positive and remaining ones are positive. And, in the end
     * of the day, the method returns the weighted average of all results.
     * @param predictions
     * @param classes
     * @return prediction the precision for each class plus a weighted precision of the predictions made by the classifier
     */
    @Override
    public double[] metric_score() {
        /** Arrays of size s, True Negative and Predicted Positive, that contain the countings for each class, and an array that contains the number of times each class appears in classes*/
        int[] TP = new int[s],
        PP = new int[s],
        Nc = new int[s];
        /**An array that will contain the precision for each class */
        double[] precisions = new double[s+1];

        /* Precision = TP/PP */
        for(int c=0; c<s; c++){
            for(int i=0; i<preds.length; i++) {
                if(c==0) Nc[classes[i]]++;
                /* True Positives */
                if(preds[i] == classes[i] && c == preds[i])
                    TP[c]++;
                /* Predicted Positives */
                if(c == preds[i])
                    PP[c]++;
            }
            /* precision per class */
            precisions[c] = (TP[c]==0) ? 0 : (double) 100*TP[c]/PP[c];
            /* Weighted average of all precisions */
            precisions[s] += precisions[c]*Nc[c]/classes.length;
        }
        return precisions;
    }

    
}