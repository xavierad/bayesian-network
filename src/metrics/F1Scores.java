package metrics;

/**
 * This class implements the interface IMetrics. It stores two arrays of type int.
 */
public class F1Scores implements IMetrics {

    /** An array attribute that will contain the classifier sensitivities of prediction, the first positions for each class and the last one a weighted average */
    double[] sensitivities;
    /** An array attribute that will contain the classifier precision of prediction, the first positions for each class and the last one a weighted average */
    double[] precisions;

    /**
     * F1Scores' contructor receives two arrays of type int and stores them in memory.
     * @param sens a sensitivities array
     * @param prec a precisions array
     */
    public F1Scores(double[]sens, double[] prec) {        
        precisions = prec;
        sensitivities = sens;
    }

    /**
     * This method computes the F1-score for each class and a weighted average as well of the classifier. It
     * makes the use of the sensitivity and precision scores.
     * @return f1-score a score
     */
    @Override
    public double[] metric_score() {

        double[] f1scores = new double[precisions.length];

        for (int i = 0; i < f1scores.length-1; i++) {
            f1scores[i] = (precisions[i]==0 || sensitivities[i]==0) ? 0 : 2*precisions[i]*sensitivities[i] / (precisions[i]+sensitivities[i]);
            f1scores[f1scores.length-1] += f1scores[i]/(f1scores.length-1);
        }
        return f1scores;
    }

    
}