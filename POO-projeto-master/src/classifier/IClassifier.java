package classifier;
import data.*;

/**
 * IClassifier
 */
public interface IClassifier {

    void build(Dataset traind);
    int[] predict(Dataset testd);
}
