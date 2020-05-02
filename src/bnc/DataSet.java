package bnc;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Data
 */
public class DataSet {

    LinkedList<String[]> data = new LinkedList<String[]>();
    Feature[] features;


    /* DataSet's constructor allows to preprocess data creating an array of features */  
    public DataSet(LinkedList<String[]> data) {
        this.data = data;
        this.features = new Feature[getNumberOfFeatures()]; //dúvida: como é que não pede a string para o constructor de feature?

        for(int i=0; i<features.length; i++) {
            features[i].name = data.getFirst()[i];

            for(String[] values : data) {                     
                features[i].values.add(Integer.valueOf(values[i]));
            } 
        }
    }

    /* To get the number of features */
    public int getNumberOfFeatures(){
        return data.getFirst().length-1;
    }

    /* To get the size of data */
    public int getDataSize() {
        return data.size()-1;
    }     

    @Override
    public String toString() {
        String s = new String();
        for(String[] d : data) {
            s += Arrays.toString(d) + '\n';
        }
        return s;
    }   
}