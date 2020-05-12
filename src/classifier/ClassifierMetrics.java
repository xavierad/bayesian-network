package classifier;

/**
 * This class provides an object with metrics that scores the classifier predictions.
 */
public class ClassifierMetrics {

    /** An attribute that will contain the classifier accuray of prediction */
    double accuracy;
    /** An array attribute that will contain the classifier sensitivities of prediction, the first positions for each class and the last one a weighted average */
    double[] sensitivities;
    /** An array attribute that will contain the classifier specificities of prediction, the first positions for each class and the last one a weighted average */
    double[] specificities;
    /** An array attribute that will contain the classifier f1-scores of prediction, the first positions for each class and the last one a weighted average */
    double[] f1scores;

    //antes
    /** An attribute that will store the number of configurations of the class variable */
    int s;

    /**
     * This constructor will allocate memory. It must receive the type of classifier and the dataset.
     */
    public ClassifierMetrics(int[] predictions, int[] classes) {

        // dúvida!!
        IMetrics acc = new Accuracy(predictions, classes);
        accuracy = acc.metric_score(predictions, classes)[0];

        IMetrics sens = new Sensitivities(predictions, classes);
        sensitivities = sens.metric_score(predictions, classes);

        IMetrics spec = new Specificities(predictions, classes);
        specificities = spec.metric_score(predictions, classes);

        IMetrics prec = new Precisions(predictions, classes);

        IMetrics f1score = new F1Scores(specificities, prec.metric_score(predictions, classes));
        f1scores = f1score.metric_score(predictions, classes);

        //antes
        /*for(int it : classes)
            s = Math.max(s, it+1);
        this.accuracy = getAccuracy(predictions, classes);
        this.sensitivities = getSensitivity(predictions, classes);
        this.specificities = getSpecitivity(predictions, classes);
        this.f1scores = getF1Score(predictions, classes);*/
    }


    /**
     * This method will compute the accuracy, the proportion of correct predictions, making a comparison with given classes, over all prediction
     * @param predictions
     * @param classes
     * @return accuracy the rate of instances of predictions that are equal to instances of classes
     */
    protected double getAccuracy(int[]predictions, int[]classes) {
        // number of correct predictions
        int ncp=0;

        for (int i=0; i<predictions.length; i++)
            if(predictions[i] == classes[i])
                ncp++;

        return (double)100*ncp/predictions.length;
    }


    /**
     * This method will compute a weighted average sensitivity of the classifier and for each class.
     * For each class (in predictions) it will compute the sensitivity (all true positives over all true negatives plus false positives), assuming that
     * the current one is the posivite and remaining ones are negative. And, in the end
     * of the day, the method returns the weighted average of all results.
     * @param predictions
     * @param classes
     * @return sensitivities sensitivity for each class plus a weighted sensitivity of the predictions made by the classifier
     */
    protected double[] getSensitivity(int[]predictions, int[]classes) {
        /** Arrays of size s, True Positive and False Negative, that contain the countings for each class, and an array that contains the number of times each class appears in classes*/
        int[] TP = new int[s],
              FN = new int[s],
              Nc = new int[s];
        /**An array that will contain the sensitivity for each class and the weighted average */
        double[] sensitivities = new double[s+1];

        /* Sensitivity = TP / (TP + FN) */
        for(int c=0; c<s; c++){
            for(int i=0; i<predictions.length; i++) {
                if(c==0) Nc[classes[i]]++;
                /* True Positives */
                if(predictions[i] == classes[i] && c == predictions[i])
                    TP[c]++;
                /* False Negatives */
                else if(predictions[i] != classes[i] && c != predictions[i])
                    FN[c]++;
            }
            /* sensitivity per class */
            sensitivities[c] = (TP[c]==0) ? 0 : (double)100*TP[c]/(TP[c] + FN[c]);
            /* Weighted average of all sensitivities */
            sensitivities[s] += sensitivities[c]*Nc[c]/classes.length;
        }
        return sensitivities;
    }

    /**
     * This method will compute a weighted average specitivity of the classifier.
     * For each class (in predictions) it will compute the specitivity (all true negatives over all true negatives plus false positives), assuming that
     * the current one is the negative and remaining ones are positive. And, in the end
     * of the day, the method returns the weighted average of all results.
     * @param predictions
     * @param classes
     * @return specificities specitivity for each class plus a weighted specitivity of the predictions made by the classifier
     */
    protected double[] getSpecitivity(int[]predictions, int[]classes) {
        /** Arrays of size s, True Negative and False Positive, that contain the countings for each class, and an array that contains the number of times each class appears in classes*/
        int[] TN = new int[s],
              FP = new int[s],
              Nc = new int[s];
        /**An array that will contain the specitivity for each class and the weighted average */
        double[] specificities = new double[s+1];

        /* Specitivity = TN / (TN + FP) */
        for(int c=0; c<s; c++){
            for(int i=0; i<predictions.length; i++) {
                if(c==0) Nc[classes[i]]++;
                /* True Negatives */
                if(predictions[i] == classes[i] && c != predictions[i])
                    TN[c]++;
                /* False Positives */
                else if(predictions[i] != classes[i] && c == predictions[i])
                    FP[c]++;
            }
            /* specitivity per class */
            specificities[c] = (TN[c]==0) ? 0 : (double) 100*TN[c]/(TN[c] + FP[c]);
            /* Weighted average of all specificities */
            specificities[s] += specificities[c]*Nc[c]/classes.length;
        }
        return specificities;
    }


    /**
     * This method will compute a weighted average precision of the classifier.
     * For each class (in predictions) it will compute the precision (all true positives over predicted positives), assuming that
     * the current one is the positive and remaining ones are positive. And, in the end
     * of the day, the method returns the weighted average of all results.
     * @param predictions
     * @param classes
     * @return prediction precision for each class plus a weighted precision of the predictions made by the classifier
     */
    protected double[] getPrecision(int[]predictions, int[]classes) {
         /** Arrays of size s, True Negative and Predicted Positive, that contain the countings for each class, and an array that contains the number of times each class appears in classes*/
         int[] TP = new int[s],
             PP = new int[s],
             Nc = new int[s];
         /**An array that will contain the precision for each class */
         double[] precisions = new double[s+1];

         /* Precision = TP/PP */
         for(int c=0; c<s; c++){
             for(int i=0; i<predictions.length; i++) {
                 if(c==0) Nc[classes[i]]++;
                 /* True Positives */
                 if(predictions[i] == classes[i] && c == predictions[i])
                     TP[c]++;
                 /* Predicted Positives */
                 if(c == predictions[i])
                     PP[c]++;
             }
             /* precision per class */
             precisions[c] = (TP[c]==0) ? 0 : (double) 100*TP[c]/PP[c];
             /* Weighted average of all precisions */
             precisions[s] += precisions[c]*Nc[c]/classes.length;
         }
         return precisions;
    }

    /**
     * This method will compute the F1-score for each class and a weighted average as well of the classifier. It
     * will make use of the sensitivity and precision scores.
     * @param predictions
     * @param classes
     * @return f1-score a score
     */
    protected double[] getF1Score(int[]predictions, int[]classes) {
        double[] sensitivities = getSensitivity(predictions, classes);
        double[] precisions = getPrecision(predictions, classes);
        double[] f1scores = new double[precisions.length];

        for (int i = 0; i < f1scores.length-1; i++) {
            f1scores[i] = (precisions[i]==0 || sensitivities[i]==0) ? 0 : 2*precisions[i]*sensitivities[i] / (precisions[i]+sensitivities[i]);
            f1scores[f1scores.length-1] += f1scores[i]/(f1scores.length-1);
        }
        return f1scores;
    }


    /**
     * This method will print the resume of the classifier metrics.
     */
    @Override
    public String toString() {
        String str = String.format("%.2f", accuracy);
        for (int i=0; i<sensitivities.length; i++) {
            if(i==0)
                str += ", " + "[" + i + ": " + String.format("%.2f", sensitivities[i]);
            else if(i==sensitivities.length-1)
                str += "; " + String.format("%.2f", sensitivities[i]) + "]";
            else
                str += "; " + i + ": " + String.format("%.2f", sensitivities[i]);
        }
        for (int i=0; i<specificities.length; i++) {
            if(i==0)
                str += ", " + "[" + i + ": " + String.format("%.2f", specificities[i]);
            else if(i==specificities.length-1)
                str += "; " + String.format("%.2f", specificities[i]) + "]";
            else
                str += "; " + i + ": " + String.format("%.2f", specificities[i]);
        }
        for (int i=0; i<f1scores.length; i++) {
            if(i==0)
                str += ", " + "[" + i + ": " + String.format("%.2f", f1scores[i]);
            else if(i==f1scores.length-1)
                str += "; " + String.format("%.2f", f1scores[i]) + "]";
            else
                str += "; " + i + ": " + String.format("%.2f", f1scores[i]) ;
        }

        return str;
    }
}
