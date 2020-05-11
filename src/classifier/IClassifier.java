package classifier;
import data.*;

/**
 * IClassifier
 */
public interface IClassifier {

    void build(Dataset traind);
    int[] predict(Dataset testd);

    //Main.java
    //...print(bnc.results())
    //...print(cm(pred, real))

    //bnc.results(){
    //  instances_prints = (...)
    //  return intances_prints + cm.toString()
    //}

}
