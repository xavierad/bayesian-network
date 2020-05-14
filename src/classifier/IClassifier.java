package classifier;
import data.*;

/**
 * IClassifier Interface - An interface that allows clients to implement their own Classifiers.
 */
public interface IClassifier {
    /**
     * Method which builds/trains the classifier, according to the input training dataset.
     * @param traind training dataset
     */
    void build(Dataset traind);

    /**
     * Method which according to the input test dataset returns the predictions of the classifier.
     * @param testd test dataset
     * @return predictions in an array the same size as the number of instances in the test dataset. The prediction i corresponds to the prediction of the classifier for instance i.
     */
    int[] predict(Dataset testd);
}
