package metrics;

import metrics.Sensitivities;
import metrics.Precisions;

/**
 * This class implements the interface IMetrics. It stores two arrays of type int.
 */
public class F1Scores implements IMetrics {

    private double[] score;
    private int s;

    /**
     * F1Scores' contructor receives two arrays of type int and stores them in memory.
     * @param sens a sensitivities array
     * @param prec a precisions array
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
     * This method computes the F1-score for each class and a weighted average as well of the classifier. It
     * makes the use of the sensitivity and precision scores.
     * @return f1-score a score
     */
    @Override
    public double[] metric_score() {
        return score;
    }

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