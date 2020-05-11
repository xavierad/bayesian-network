package classifier;

import java.util.LinkedList;

/**
 * Dataset - This class helps to process and save information about data.
 * It will contain an array of type RVariable as atribute and two auxiliary method.
 * Mainly it converts a rowed data (linked list of rows) to collumned data
 * (array of collumns) in order to access to random variables easily.
 */
public class DataSet {

    LinkedList<String[]> data = new LinkedList<String[]>();

    /** An array that will contain all random variables */
    public RVariable[] random_vector;

    /**
     * Dataset's constructor allows to preprocess
     * data creating an array of features
     * @param data data that was read from a file
     */
    public DataSet(LinkedList<String[]> data) {
        this.data = data;
        this.random_vector = new RVariable[getRVDimension()];

        for(int i=0; i<random_vector.length; i++) {
            //random_vector[i] = new RVariable(data.getFirst()[i]);

            random_vector[i] = new RVariable();
            for(String[] d : data)
                random_vector[i].values.add(Integer.valueOf(d[i]));
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
        return data.size();
    }

    /*@Override
    public String toString() {
        String s = new String();
        for(String[] d : data) {
            s += Arrays.toString(d) + '\n';
        }
        return s;
    } */
}
