package data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Dataset - This class helps to process and save information about data.
 * It will contain an array of type RVariable as atribute and two auxiliary method.
 * Mainly it converts a rowed data (linked list of rows) to collumned data
 * (array of collumns) in order to access to random variables easily.
 */
public class Dataset {

    /** A linked list of arrays of strings that contains data parsed through a CSV reader */
    LinkedList<String[]> data = new LinkedList<String[]>();
    /** An array that will contain all random variables */
    RVariable[] random_vector;

    /**
     * Dataset's constructor allows to preprocess
     * data creating an array of features
     * @param data data that was read from a file
     */
    public Dataset(LinkedList<String[]> data) {
        this.data = data;
        this.random_vector = new RVariable[getRVDimension()];

        for(int i=0; i<random_vector.length; i++) {
            // Allocation memory
            random_vector[i] = new RVariable();

            // Processing data: get name and values for each collumn of data (random variable)
            ListIterator<String[]> d = data.listIterator();
            random_vector[i].name = d.next()[i];
            //System.out.println(i + " " + random_vector[i].name);
            while (d.hasNext()) 
                random_vector[i].values.add(Integer.valueOf(d.next()[i]));
            random_vector[i].calcMaxValue();
        }
    }

    /**
     * This method gets the number
     * of random variables in random_vector.
     * @return data.getFirst().length (the random vector length)
     */
    public int getRVDimension(){
        return data.getFirst().length;
    }

    /**
     * This method gets the size of
     * data, the amount of instances.
     * @return data.size() (the size).
     */
    public int getDataSize() {
        return data.size()-1;
    }

    /**
     * This method will return a random variable by accessing random vector with index i.
     * @param i an index to access in random_vector
     * @return random_vector[i] a random variable
     */
    public RVariable getRVariable(int i) {
        return random_vector[i];
    }

}
