package metrics;

/**
 * This class implements the interface IMetrics. It stores two arrays of type int.
 */
public class Specificities implements IMetrics {

    /**An array that will contain the specitivity for each class and the weighted average */
    private double[] score;

    /**
     *
     */
    public Specificities(int[] preds, int[] classes) {
        // Auxiliary variable: number of classes
        int s = 0;
        for(int it : classes)
            s = Math.max(s, it+1);

        /** Arrays of size s, True Negative and False Positive, that contain the countings for each class, and an array that contains the number of times each class appears in classes*/
        int[] TN = new int[s],
              FP = new int[s],
              Nc = new int[s];

        score = new double[s+1];

        /* Specitivity = TN / (TN + FP) */
        for(int c=0; c<s; c++){
            for(int i=0; i<preds.length; i++) {
                if(c==0) Nc[classes[i]]++;
                /* True Negatives */
                if(preds[i] == classes[i] && c != preds[i])
                    TN[c]++;
                /* False Positives */
                else if(preds[i] != classes[i] && c == preds[i])
                    FP[c]++;
            }
            /* specitivity per class */
            score[c] = (TN[c]==0) ? 0 : (double) 100*TN[c]/(TN[c] + FP[c]);
            /* Weighted average of all specificities */
            score[s] += score[c]*Nc[c]/classes.length;
        }
    }

    /**
     * This method returns a weighted average specitivity of the classifier.
     * @return specificities the specitivity for each class plus a weighted specitivity of the predictions made by the classifier.
     */
    @Override
    public double[] metric_score() {
      return score;
    }

    /**
     * This method will return a string with the specificties scores as percentages.
     * The scores for each class and average will be seperated by commmas.
     */
    @Override
    public String toString() {
        String str = "";
        for (int i=0; i<score.length; i++) {
            if(i==0)
                str += ", " + "[" + i + ": " + String.format("%.2f", score[i]) + '%';
            else if(i==score.length-1)
                str += "; " + String.format("%.2f", score[i]) + '%' + "]";
            else
                str += "; " + i + ": " + String.format("%.2f", score[i]) + '%';
        }
        return str;
    }
}
