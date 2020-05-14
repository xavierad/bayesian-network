package metrics;

import metrics.Sensitivities;
import metrics.Precisions;

/**
 * This class implements the interface IMetrics. It stores two arrays of type int.
 */
public class F1Scores implements IMetrics {

    /** An array that contains the score for each class and the weighted average score in last position */
    private double[] score;

    /**
     * F1Scores' contructor: computes the f1-score for each class and weighted average for the whole input dataset.
     * The f1-scores metrics are only computed on the constructor and the score/result is saved for quick and easy acesss.
     * @param preds a predictions array
     * @param classes a class array
     */
    public F1Scores(int[]preds, int[] classes) {

        // The maximum value that classes contains 
        int s=0;
        for(int it : classes)
            s = Math.max(s, it+1);

        // The number of instances that belongs to a class c
        int[] Nc = new int[s];
        for(int i=0; i<preds.length; i++)
            Nc[classes[i]]++;

        // Computing sensitivities
        IMetrics sens = new Sensitivities(preds, classes);
        // An array that contains the sensitivity per class and the weighted average
        double[] sensitivities = sens.metric_score();
        // Computing precisions */
        IMetrics prec = new Precisions(preds, classes);
        // An array that contains the sensitivity per class and the weighted average
        double[] precisions = prec.metric_score();

        score = new double[s+1];

        // F1-scores operation: (2*precision*sensitivity / precision + sensitivity)
        for (int c = 0; c < s; c++) {
            // F1-score per class
            score[c] = (precisions[c]==0 || sensitivities[c]==0) ? 0 : 2*precisions[c]*sensitivities[c] / (precisions[c]+sensitivities[c]);
            // Weighted average of all F1-scores
            score[s] += score[c]*Nc[c]/classes.length;
        }
    }

    /**
     * This method returns the F1-score for each class and a weighted average as well of the classifier.
     * @return f1-measurament scores
     */
    @Override
    public double[] metric_score() {
        return score;
    }

    /**
     * This method will return a string with the F1-Score scores as percentages.
     * The scores for each class and average will be seperated by commmas.
     */
    @Override
    public String toString() {
        String str = "";
        for (int i=0; i<score.length; i++) {
            if(i==0)
                str += "F1-Score: [" + i + ": " + String.format("%.2f", score[i]) + '%';
            else if(i==score.length-1)
                str += "; " + String.format("%.2f", score[i]) + '%' + "]";
            else
                str += "; " + i + ": " + String.format("%.2f", score[i]) + '%';
        }
        return str;
    }
}
