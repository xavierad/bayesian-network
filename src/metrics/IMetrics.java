package metrics;

/**
 * This interface gets a metric score.
 */
public interface IMetrics {
    
    /**
     * This method will return a result array of type double.
     * @return metric an array that contains the results for each class and the average.
     */
    public double[] metric_score();    
}