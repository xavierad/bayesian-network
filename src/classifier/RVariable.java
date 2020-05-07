package classifier;

import java.util.LinkedList;

/**
 * RVariable - This class provides allocation of values and 
 * the maximum value of a certain random variable (feature or class).
 */
public class RVariable {

    //String name;
    /** The maximum value of this random variable */
    int max_value;
    /** The values that this random variable contains */
    LinkedList<Integer> values;

    /**
     * RVariable's constructor will allocate
     * memory to values atribute for a random variable.
     */
    public RVariable() {
        this.values = new LinkedList<Integer>();
    }
    
    /**
     * This method calculates the maximum value of
     * the random variable and assigns to max_value atribute.
     */
    public void calcMaxValue() {
        int max=0;
        for(int value:values)
            max = (value > max) ? value : max;
        this.max_value = max;
    }
}