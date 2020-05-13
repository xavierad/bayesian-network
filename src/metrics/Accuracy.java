package metrics;

/**
 * Accuracy
 */
public class Accuracy implements IMetrics {
    int[] preds;
    int[] classes;

    public Accuracy(int[] p, int[] c) {
        preds = p;
        classes = c;
    }

    @Override
    public double[] metric_score(int[] preds, int[] classes) {
        
        /* number of correct predictions */
        int ncp=0;

        for (int i=0; i<preds.length; i++)
            if(preds[i] == classes[i])
                ncp++;
        double[]res = new double [1];
        res[0] = (double)100*ncp/preds.length;
        return res;
    }    
}