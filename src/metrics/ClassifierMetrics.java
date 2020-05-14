package metrics;

/**
 * This class provides an object with metrics that scores the classifier predictions.
 */
public class ClassifierMetrics {

    /** An attribute that contains the classifier accuracy of prediction */
    IMetrics acc;
    /** An attribute that contains the classifier sensitivities of prediction, the first positions for each class and the last one a weighted average */
    IMetrics sens;
    /** An attribute that contains the classifier specifities of prediction, the first positions for each class and the last one a weighted average */
    IMetrics spec;
    /** An attribute that contains the classifier f1-scores of prediction, the first positions for each class and the last one a weighted average */
    IMetrics f1score;

    /**
     * This constructor allocates memory. It must receive the type of classifier and the dataset.
     */
    public ClassifierMetrics(int[] predictions, int[] classes) {

        acc = new Accuracy(predictions, classes);

        sens = new Sensitivities(predictions, classes);

        spec = new Specificities(predictions, classes);

        f1score = new F1Scores(predictions, classes);
    }

    /**
     * This method prints the resume of the classifier metrics.
     */
    @Override
    public String toString() {
               
        return acc.toString() + ", " + sens.toString() + ", " + spec.toString() + ", " + f1score.toString();
    }
}
