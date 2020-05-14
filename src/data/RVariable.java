package data;

import java.util.LinkedList;

/**
 * RVariable - This class provides allocation of values and
 * the maximum value of a certain random variable (feature or class).
 */
public class RVariable {

    /** The name of the random variable */
    String name;
    /** The maximum value of this random variable */
    int max_value;
    /** The values that this random variable contains */
    LinkedList<Integer> values;

    /**
     * RVariable's constructor will allocate memory to values atribute for a random variable.
     */
    public RVariable() {
        this.values = new LinkedList<Integer>();
    }

    /**
     * This method calculates the maximum value of the random variable and assigns to max_value atribute.
     */
    public void calcMaxValue() {
        int max=0;
        for(int value:values)
            max = (value > max) ? value : max;
        this.max_value = max;
    }

    /**
     * This method gets the maximum value of a random variable.
     * @return max_value the maximum value of a random variable.
     */
    public int getMax_value() {
        return max_value;
    }

    /**
     * This method gets a value stored in a certain the position.
     * @param i an index to access in values list
     * @return values.get(i) the values stored in a random variable.
     */
    public Integer getValue(int i) {
        return values.get(i);
    }

    /**
     * This method gets the values stored in a random variable. It converts the list of type Integer to an array of type int.
     * @return arr an array of type int that contains the values in a random variable.
     */
    public int[] getValues(){
        int[] arr = new int[values.size()];
        for (int i=0; i<values.size(); i++)
            arr[i] = values.get(i);
        return arr;
    }

    /**
     * This method will return a random variable name.
     * @return name the name of a random variable.
     */
    public String getRVName() {
        return name;
    }

}
