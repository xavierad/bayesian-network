package classifier;

/**
 * IClassifier
 */
public interface IClassifier {

    void build(Dataset traind);
    int[] predict(Dataset testd);
}
