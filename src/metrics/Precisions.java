package metrics;

/**
 * This class implements the interface IMetrics.
 */
public class Precisions implements IMetrics {
    /** Var*/
    private double[] score;

    /**
     * Precision Constuctor: computes the precision for each class and weighted precision for the whole input dataset.
     * The precision measurement is only computed on the constructor and the score/result is saved for quick and easy acess.
     */
    public Precisions(int[] preds, int[] classes) {
        // Auxiliary variable: number of classes
        int s = 0;
        for(int it : classes)
            s = Math.max(s, it+1);

        // Arrays of size s, True Negative and Predicted Positive, that contain the countings for each class, and an array that contains the number of times each class appears in classes
        int[] TP = new int[s],
              PP = new int[s],
              Nc = new int[s];
        //An array that will contain the precision for each class
        score = new double[s+1];

        // Precision = TP/PP
        for(int c=0; c<s; c++){
            for(int i=0; i<preds.length; i++) {
                if(c==0) Nc[classes[i]]++;
                // True Positives
                if(preds[i] == classes[i] && c == preds[i])
                    TP[c]++;
                // Predicted Positives
                if(c == preds[i])
                    PP[c]++;
            }
            // Precision per class
            score[c] = (TP[c]==0) ? 0 : (double) 100*TP[c]/PP[c];
            // Weighted average of all precisions
            score[s] += score[c]*Nc[c]/classes.length;
        }
    }

    /**
     * This method returns the precisions for each class and weighted precision for the complete dataset.
     * @return prediction the precision for each class plus a weighted precision of the predictions
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
