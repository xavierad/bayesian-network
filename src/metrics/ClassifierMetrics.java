package metrics;

/**
 * This class provides an object with metrics that scores the classifier predictions.
 */
public class ClassifierMetrics {

    /** An attribute that will contain the classifier accuray of prediction */
    IMetrics acc;
    /** An array attribute that will contain the classifier sensitivities of prediction, the first positions for each class and the last one a weighted average */
    IMetrics sens;
    IMetrics spec;
    IMetrics f1score;

    /**
     * This constructor will allocate memory. It must receive the type of classifier and the dataset.
     */
    public ClassifierMetrics(int[] predictions, int[] classes) {

        acc = new Accuracy(predictions, classes);

        sens = new Sensitivities(predictions, classes);

        spec = new Specificities(predictions, classes);

        f1score = new F1Scores(predictions, classes);
    }

    /**
     * This method will print the resume of the classifier metrics.
     */
    @Override
    public String toString() {
               
        return acc.toString() + sens.toString() + spec.toString() + f1score.toString();
    }
}
