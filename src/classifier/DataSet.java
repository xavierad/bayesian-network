package classifier;

import java.util.LinkedList;

/**
 * Dataset - This class helps to process and save information about data. 
 * It will contain an array of type RVariable as atribute and two auxiliary method. 
 * Mainly it converts a rowed data (linked list of rows) to collumned data
 * (array of collumns) in order to access to random variables easily.
 */
public class Dataset {

    // acho que não faz sentido guardar na classe data, dado que não vamos utilizar senão apenas para carregar para o random_vector
    //LinkedList<String[]> data = new LinkedList<String[]>();
    RVariable[] random_vector;
 
    /**
     * Dataset's constructor allows to preprocess
     * data creating an array of features
     * @param data data that was read from a file
     */
    public Dataset(LinkedList<String[]> data) {
        //this.data = data;
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
     * @return random_vector.length (the random vector length)
     */
    public int getRVDimension(){
        return random_vector.length;
    }

    /* To get the size of data */

    /**
     * This method gets the size of
     * data, the amount of instances.
     * @return random_vector[0].values.size() (the size).
     */
    public int getDataSize() {
        return random_vector[0].values.size();
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