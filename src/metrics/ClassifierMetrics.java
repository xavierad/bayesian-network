package metrics;

/**
 * This class provides an object with metrics that scores the classifier predictions.
 */
public class ClassifierMetrics {

    /** An attribute that will contain the classifier accuray of prediction */
    double accuracy;
    /** An array attribute that will contain the classifier sensitivities of prediction, the first positions for each class and the last one a weighted average */
    double[] sensitivities;
    /** An array attribute that will contain the classifier specificities of prediction, the first positions for each class and the last one a weighted average */
    double[] specificities;
    /** An array attribute that will contain the classifier f1-scores of prediction, the first positions for each class and the last one a weighted average */
    double[] f1scores;

    /**
     * This constructor will allocate memory. It must receive the type of classifier and the dataset.
     */
    public ClassifierMetrics(int[] predictions, int[] classes) {

        IMetrics acc = new Accuracy(predictions, classes);
        accuracy = acc.metric_score()[0];

        IMetrics sens = new Sensitivities(predictions, classes);
        sensitivities = sens.metric_score();

        IMetrics spec = new Specificities(predictions, classes);
        specificities = spec.metric_score();

        IMetrics prec = new Precisions(predictions, classes);

        IMetrics f1score = new F1Scores(specificities, prec.metric_score());
        f1scores = f1score.metric_score();
    }

    /**
     * This method will print the resume of the classifier metrics.
     */
    @Override
    public String toString() {
        String str = String.format("%.2f", accuracy) + '%';
        for (int i=0; i<sensitivities.length; i++) {
            if(i==0)
                str += ", " + "[" + i + ": " + String.format("%.2f", sensitivities[i]) + '%';
            else if(i==sensitivities.length-1)
                str += "; " + String.format("%.2f", sensitivities[i]) + '%' + "]";
            else
                str += "; " + i + ": " + String.format("%.2f", sensitivities[i]) + '%';
        }
        for (int i=0; i<specificities.length; i++) {
            if(i==0)
                str += ", " + "[" + i + ": " + String.format("%.2f", specificities[i]) + '%';
            else if(i==specificities.length-1)
                str += "; " + String.format("%.2f", specificities[i]) + '%' + "]";
            else
                str += "; " + i + ": " + String.format("%.2f", specificities[i]) + '%';
        }
        for (int i=0; i<f1scores.length; i++) {
            if(i==0)
                str += ", " + "[" + i + ": " + String.format("%.2f", f1scores[i]) + '%';
            else if(i==f1scores.length-1)
                str += "; " + String.format("%.2f", f1scores[i]) + '%' + "]";
            else
                str += "; " + i + ": " + String.format("%.2f", f1scores[i]) + '%' ;
        }

        return str;
    }
}
