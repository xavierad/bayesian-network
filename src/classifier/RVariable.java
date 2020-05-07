package classifier;

import java.util.LinkedList;

/**
 * RVariable - This class provides allocation of values and 
 * the maximum value of a certain random variable (feature or class).
 */
public class RVariable {

    //String name;
    int max_value;
    LinkedList<Integer> values;

    /**
     * RVaribel's constructor will allocate
     * memory to values atribute for a random variable.
     */
    public RVariable() {
        this.values = new LinkedList<Integer>();
    }

    /* To calculate the maximum value of this feature */
    
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