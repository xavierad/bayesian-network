package classifier;

import classifier.Dataset;
import classifier.IClassifier;

/**
 * ClassifierMetrics - This class provides an object with metrics that scores the classifier predictions
 */
public class ClassifierMetrics {

    /** A type of classifier */
    IClassifier classifier;
    /** A dataset */
    Dataset data;

    double accuracy;
    double[] sensitivities;
    double[] specitivities;
    double[] f1scores;

    // será que é necessário os parâmetros data e classifier? não é só preciso as arrays predictions e classes?
    /**
     * This constructor will allocate memory. It must receive the type of classifier and the dataset.
     * @param classifier
     * @param data
     */
    public ClassifierMetrics(IClassifier classifier, Dataset data) {
        this.classifier = classifier;
        this.data = data;

        int[] predictions = classifier.predict(data);
        int[] classes = new int[predictions.length];
        for (int i=0; i<classes.length; i++)
            classes[i] = data.random_vector[data.random_vector.length - 1].values.get(i);        

        this.accuracy = getAccuracy(predictions, classes);
        this.sensitivities = getSensitivity(predictions, classes);
        this.specitivities = getSpecitivity(predictions, classes);
        this.f1scores = getF1Score(predictions, classes);
    }

    //
    //para imprimir com duas casas decimais, pôr por exemplo System.out.println("value: %.2f" + value); ou String result = String.format("%.2f", value);
    //

    /**
     * This method will compute the accuracy, the proportion of correct predictions, making a comparison with given classes, over all prediction
     * @param predictions
     * @param classes
     * @return accuracy the rate of instances of predictions that are equal to instances of classes
     */
    protected double getAccuracy(int[]predictions, int[]classes) {
        /* number of correct predictions */
        int ncp=0;

        for (int i=0; i<predictions.length; i++)             
            if(predictions[i] == classes[i])
                ncp++;       

        return (double)100*ncp/predictions.length;
    }

    //
    // [0:<<metric_for_positive_0>>,...,v:<<metric_for_positive_z-1>>,<<metric_weighted_average>>]
    //

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
        /** The maximum value of classes */
        int s = data.random_vector[data.getRVDimension()].max_value;
        /** Arrays of size s, True Positive and False Negative, that contain the countings for each class, and an array that contains the number of times each class appears in classes*/
        int[] TP = new int[s+1],
            FN = new int[s+1],
            Nc = new int[s+1];
        /**An array that will contain the sensitivity for each class and the weighted average */
        double[] sensitivities = new double[s+2];
        
        /* Sensitivity = TP / (TP + FN) */
        for(int c=0; c<=s; c++){
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
            sensitivities[s+1] += sensitivities[c]*Nc[c]/classes.length;
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
     * @return specitivities specitivity for each class plus a weighted specitivity of the predictions made by the classifier
     */
    protected double[] getSpecitivity(int[]predictions, int[]classes) {
        /** The maximum value of classes */
        int s = data.random_vector[data.getRVDimension()-1].max_value;
        /** Arrays of size s, True Negative and False Positive, that contain the countings for each class, and an array that contains the number of times each class appears in classes*/
        int[] TN = new int[s+1],
            FP = new int[s+1],
            Nc = new int[s+1];
        /**An array that will contain the specitivity for each class and the weighted average */
        double[] specitivities = new double[s+2];
        
        /* Specitivity = TN / (TN + FP) */
        for(int c=0; c<=s; c++){
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
            specitivities[c] = (TN[c]==0) ? 0 : (double) 100*TN[c]/(TN[c] + FP[c]);
            /* Weighted average of all specitivities */
            specitivities[s+1] += specitivities[c]*Nc[c]/classes.length;
        }       
        return specitivities;
    } 


    /**
     * This method will compute a weighted average precision of the classifier. 
     * For each class (in predictions) it will compute the precision (all true positives over predicted positives), assuming that 
     * the current one is the positive and remaining ones are positive. And, in the end 
     * of the day, the method returns the weighted average of all results.
     * @param predictions
     * @param classes
     * @return prediction a weighted precision of the predictions made by the classifier 
     */
    protected double[] getPrecision(int[]predictions, int[]classes) {
         /** The maximum value of classes */
         int s = data.random_vector[data.getRVDimension()-1].max_value;
         /** Arrays of size s, True Negative and Predicted Positive, that contain the countings for each class, and an array that contains the number of times each class appears in classes*/
         int[] TP = new int[s+1],
             PP = new int[s+1],
             Nc = new int[s+1];
         /**An array that will contain the precision for each class */
         double[] precisions = new double[s+2];
         
         /* Precision = TP/PP */
         for(int c=0; c<=s; c++){
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
             precisions[s+1] += precisions[c]*Nc[c]/classes.length;
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

    

    @Override
    public String toString() {
        String str = "Resume: " + '\n' + String.format("%.2f", accuracy);
        for (int i=0; i<sensitivities.length; i++) {
            if(i==0)
                str += '\n' + "[" + i + ": " + String.format("%.2f", sensitivities[i]);
            else if(i==sensitivities.length-1) 
                str += "; " + String.format("%.2f", sensitivities[i]) + "]";
            else 
                str += "; " + i + ": " + String.format("%.2f", sensitivities[i]);
        }
        for (int i=0; i<specitivities.length; i++) {
            if(i==0) 
                str += '\n' + "[" + i + ": " + String.format("%.2f", specitivities[i]);
            else if(i==specitivities.length-1)
                str += "; " + String.format("%.2f", specitivities[i]) + "]";
            else 
                str += "; " + i + ": " + String.format("%.2f", specitivities[i]);
        }
        for (int i=0; i<f1scores.length; i++) {
            if(i==0) 
                str += '\n' + "[" + i + ": " + String.format("%.2f", f1scores[i]);
            else if(i==f1scores.length-1)
                str += "; " + String.format("%.2f", f1scores[i]) + "]";
            else
                str += "; " + i + ": " + String.format("%.2f", f1scores[i]) ;
        }

        return str;
    }

    


}