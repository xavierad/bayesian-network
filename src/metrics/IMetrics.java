package metrics;

/**
 * IClassifierMetrics
 */
public interface IMetrics {
    
    double[] metric_score(int[] preds, int[] classes);    
}