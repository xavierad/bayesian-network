package data;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Dataset - This class helps to process and save information about data.
 * It will contain an array of type RVariable as atribute and two auxiliary method.
 * Mainly it converts a rowed data (linked list of rows) to collumned data
 * (array of collumns) in order to access to random variables easily.
 */
public class Dataset {

    /** An array that will contain all random variables */
    RVariable[] random_vector;

    /**
     * Dataset's constructor allows to preprocess
     * data creating an array of features
     * @param data data that was read from a file
     */
    public Dataset(LinkedList<String[]> data) {
        this.random_vector = new RVariable[data.getFirst().length];

        for(int i=0; i<random_vector.length; i++) {
            // Allocation memory
            random_vector[i] = new RVariable();

            // Processing data: get name and values for each collumn of data (random variable)
            ListIterator<String[]> d = data.listIterator();
            random_vector[i].name = d.next()[i];
            while (d.hasNext())
                random_vector[i].values.add(Integer.valueOf(d.next()[i]));
            random_vector[i].calcMaxValue();
        }
    }

    /**
     * This method gets the number of random variables in random_vector.
     * @return data.getFirst().length (the random vector length)
     */
    public int getRVDimension(){
        return random_vector.length;
    }

    /**
     * This method gets the size of data, the amount of instances.
     * @return data.size() (the size).
     */
    public int getDataSize() {
        return random_vector[0].getValues().length;
    }

    /**
     * This method will return a random variable by accessing the random vector with index i.
     * @param i an index to access in random_vector
     * @return random_vector[i] a random variable.
     */
    public RVariable getRVariable(int i) {
        return random_vector[i];
    }
}
