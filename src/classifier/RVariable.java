package classifier;

import java.util.LinkedList;

/**
 * RVariable
 */
public class RVariable {

    String name;
    int max_value;
    int min_value;
    LinkedList<Integer> values;


    public RVariable(String name) {
        this.name = name;
    }

    /* To calculate the maximum value of this feature */
    public void calcMaxValue() {
        int max = 0;
        for(Integer value:values){
            this.max_value = value > max ? value : max;
        }
    }

    /* To calculate the mminimum value of this feature */
    public void calcMinValue() {
        int min = 0;
        for(Integer value:values){
            this.min_value = value < min ? value : min;
        }
    }
}