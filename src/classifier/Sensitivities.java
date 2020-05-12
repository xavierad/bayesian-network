package classifier;

/**
 * Sensitivities
 */
public class Sensitivities implements IMetrics{

    int[] preds;
    int[] classes;
    /** An attribute that will store the number of configurations of the class variable */
    int s;

    public Sensitivities(int[] p, int[] c) {
        for(int it : c)
            s = Math.max(s, it+1);
        preds = p;
        classes = c;
    }

    @Override
    public double[] metric_score(int[] preds, int[] classes) {
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