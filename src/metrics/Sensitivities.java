package metrics;

/**
 * This class implements the interface IMetrics.
 */
public class Sensitivities implements IMetrics{

    /**An array that will contain the specitivity for each class and the weighted average */
    private double[] score;

    /**
     * Sensitivities' contructor: computes the precision for each class and weighted precision for the whole input dataset.
     * The precision measurement is only computed on the constructor and the score/result is saved for quick and easy acesss.
     * @param preds a predictions array
     * @param classes a class array
     */
    public Sensitivities(int[] preds, int[] classes) {
        // Auxiliary variable: number of classes
        int s = 0;
        for(int it : classes)
            s = Math.max(s, it+1);

        // Arrays of size s, True Positive and False Negative, that contain the countings for each class, and an array that contains the number of times each class appears in classes
        int[] TP = new int[s],
              FN = new int[s],
              Nc = new int[s];
        // An array that will contain the sensitivity for each class and the weighted average
        score = new double[s+1];

        // Sensitivity = TP / (TP + FN)
        for(int c=0; c < s; c++){
            for(int i=0; i<preds.length; i++) {
                if(c==0) Nc[classes[i]]++;
                /* True Positives */
                if(preds[i] == classes[i] && c == preds[i])
                    TP[c]++;
                /* False Negatives */
                else if(preds[i] != classes[i] && c != preds[i])
                    FN[c]++;
            }
            // sensitivity per class
            score[c] = (TP[c]==0) ? 0 : (double)100*TP[c]/(TP[c] + FN[c]);
            // Weighted average of all sensitivities
            score[s] += score[c]*Nc[c]/classes.length;
        }
    }

    /**
     * This method return the sensitivity for each class and weighted average sensitivity of the input dataset.
     * @return sensitivities the sensitivity for each class plus a weighted sensitivity of the predictions made by the classifier.
     */
    @Override
    public double[] metric_score() {
        return score;
    }

    /**
     * This method will return a string with the sensitivity scores as percentages.
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
