package classifier;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Data
 */
public class Dataset {

    LinkedList<String[]> data = new LinkedList<String[]>();
    RVariable[] random_vector;

    /* Dataset's constructor allows to preprocess data creating an array of features */  
    public Dataset(LinkedList<String[]> data) {
        this.data = data;
        this.random_vector = new RVariable[getRVDimension()]; //dúvida: como é que não pede a string para o constructor de feature?

        for(int i=0; i<random_vector.length; i++) {
            random_vector[i].name = data.getFirst()[i];

            for(String[] values : data) {                     
                random_vector[i].values.add(Integer.valueOf(values[i]));
            } 
        }
    }

    /* To get the number of features plus the class variable */
    public int getRVDimension(){
        return data.getFirst().length;
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