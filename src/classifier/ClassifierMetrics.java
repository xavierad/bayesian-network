package classifier;

import classifier.Dataset;
import classifier.IClassifier;
import classifier.RVariable;

/**
 * ClassifierMetrics - This class provides a metric method in order to score the classifier predictions
 */
public class ClassifierMetrics {

    /** A type of classifier */
    IClassifier classifier;
    /** A dataset */
    Dataset data;

    // será que é necessário os parâmetros data e classifier? não é só preciso as arrays predictions e classes?
    /**
     * This constructor will allocate memory. It must receive the type of classifier and the dataset.
     * @param classifier
     * @param data
     */
    public ClassifierMetrics(IClassifier classifier, Dataset data) {
        this.classifier = classifier;
        this.data = data;
    }

    /**
     * This method will compute the accuracy, the proportion of correct predictions, making a comparison with given classes, over all prediction
     * @param predictions
     * @param classes
     * @return accuracy the rate of instances of predictions that are equal to instances of classes
     */
    public double getAccuracy(int[]predictions, int[]classes) {
        /* number of correct predictions */
        int ncp=0;

        for (int i=0; i<predictions.length; i++) 
            
            if(predictions[i] == classes[i])
                ncp++;       

        return ncp/predictions.length;
    }

    // [0:<<metric_for_positive_0>>,...,v:<<metric_for_positive_z-1>>,<<metric_weighted_average>>]
    /**
     * This method will compute a weighted average sensitivity of the the classifier. 
     * For each class (in predictions) it will compute the sensitivity, assuiming that 
     * the current one is the posivite and remaining ones are negative. And, in the end 
     * of the day, the method returns the weighted average of all results.
     * @param predictions
     * @param classes
     * @return sensitivity a weighted sensitivity of the predictions made by the classifier
     */
    public double getSensitivity(int[]predictions, int[]classes) {
        /** The maximum value of classes */
        int s = data.random_vector[data.getRVDimension()].max_value;
        /** Arrays of size s, True Positive and False Negative, that contain the countings for each class, and an array that contains the number of times each class appears in classes*/
        int[] TP = new int[s+1],
            FN = new int[s+1],
            Nc = new int[s+1];
        /**An array that will contain the sensitivity for each class */
        double[] sensitivityC = new double[s+1];
        double sensitivity=0;
        
        /* Sensitivity = TP / (TP + FN) */
        for(int c=0; c<=s; c++){
            for(int i=0; i<predictions.length; i++) {
                Nc[classes[i]]++;
                /* True Positives */
                if(predictions[i] == classes[i] && c == predictions[i])
                    TP[c]++;
                /* False Negatives */
                else if(predictions[i] != classes[i] && c != predictions[i])
                    FN[c]++;
            }
            /* sensitivity per class */
            sensitivityC[c] = (TP[c]==0) ? 0 : TP[c]/(TP[c] + FN[c]);
            /* Weighted average of all sensitivities */
            sensitivity += sensitivityC[c]*Nc[c]/(s+1);
        }       
        return sensitivity;
    } 

    public double getSpecitivity(int[]predictions, int[]classes) {
        /** The maximum value of classes */
        int s = data.random_vector[data.getRVDimension()-1].max_value;
        /** Arrays of size s, True Negative and False Positive, that contain the countings for each class, and an array that contains the number of times each class appears in classes*/
        int[] TN = new int[s+1],
            FP = new int[s+1],
            Nc = new int[s+1];
        /**An array that will contain the specitivity for each class */
        double[] specitivityC = new double[s+1];
        double specitivity=0;
        
        /* Specitivity = TN / (TN + FP) */
        for(int c=0; c<=s; c++){
            for(int i=0; i<predictions.length; i++) {
                Nc[classes[i]]++;
                /* True Negatives */
                if(predictions[i] == classes[i] && c != predictions[i])
                    TN[c]++;
                /* False Positives */
                else if(predictions[i] != classes[i] && c == predictions[i])
                    FP[c]++;
            }
            /* specitivity per class */
            specitivityC[c] = (TN[c]==0) ? 0 : TN[c]/(TN[c] + FP[c]);
            /* Weighted average of all specitivities */
            specitivity += specitivityC[c]*Nc[c]/(s+1);
        }       
        return specitivity;
    } 

    public double getPrecision(int[]predictions, int[]classes) {
         /** The maximum value of classes */
         int s = data.random_vector[data.getRVDimension()-1].max_value;
         /** Arrays of size s, True Negative and Predicted Positive, that contain the countings for each class, and an array that contains the number of times each class appears in classes*/
         int[] TP = new int[s+1],
             PP = new int[s+1],
             Nc = new int[s+1];
         /**An array that will contain the sensitivity for each class */
         double[] precisionC = new double[s+1];
         double precision=0;
         
         /* Precision = TP/PP */
         for(int c=0; c<=s; c++){
             for(int i=0; i<predictions.length; i++) {
                 Nc[classes[i]]++;
                 /* True Positives */
                 if(predictions[i] == classes[i] && c == predictions[i])
                     TP[c]++;
                 /* Predicted Positives */
                 if(c == predictions[i])
                     PP[c]++;
             }
             /* sensitivity per class */
             precisionC[c] = (TP[c]==0) ? 0 : TP[c]/PP[c];
             /* Weighted average of all sensitivities */
             precision += precisionC[c]*Nc[c]/(s+1);
         }       
         return precision;
    }

    public double getF1Score(int[]predictions, int[]classes) {
        double sensitivity = getSensitivity(predictions, classes);
        double precision = getPrecision(predictions, classes);

        return 2*precision*sensitivity / (precision+sensitivity);
    }    
}