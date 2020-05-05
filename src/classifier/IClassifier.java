package classifier;

/**
 * IClassifier
 */
public interface IClassifier {

    void build(Dataset traind);
    void predict(Dataset testd);
}
