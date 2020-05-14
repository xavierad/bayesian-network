package metrics;

import metrics.Sensitivities;
import metrics.Precisions;

/**
 * This class implements the interface IMetrics. It stores two arrays of type int.
 */
public class F1Scores implements IMetrics {
    /** An array that will contain the F1-Scores for each class and the weighted average. */
    private double[] score;
    /** Number of classes */
    private int s;

    /**
     * F1Scores' contructor receives two arrays of type int and stores them in memory.
     * @param preds predictions of the classifier
     * @param classes test dataset classes.
     */
    public F1Scores(int[]preds, int[] classes) {
        for(int it : classes)
            s = Math.max(s, it+1);

        IMetrics sens = new Sensitivities(preds, classes);
        double[] sensitivities = sens.metric_score();

        IMetrics prec = new Precisions(preds, classes);
        double[] precisions = prec.metric_score();

        score = new double[s+1];

        int[] Nc = new int[s];

        for(int c=0; c<s; c++)
            for(int i=0; i<preds.length; i++)
                if(c==0) Nc[classes[i]]++;

        for (int c = 0; c < s; c++) {
            score[c] = (precisions[c]==0 || sensitivities[c]==0) ? 0 : 2*precisions[c]*sensitivities[c] / (precisions[c]+sensitivities[c]);
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
                str += ", " + "[" + i + ": " + String.format("%.2f", score[i]) + '%';
            else if(i==score.length-1)
                str += "; " + String.format("%.2f", score[i]) + '%' + "]";
            else
                str += "; " + i + ": " + String.format("%.2f", score[i]) + '%';
        }
        return str;
    }


}
