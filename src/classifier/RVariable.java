package classifier;

import java.util.LinkedList;

/**
 * RVariable
 */
public class RVariable {

    //String name;
    int max_value;
    LinkedList<Integer> values;

    /*public RVariable(String name) {
        this.name = name;
    }*/

    public RVariable() {
        this.values = new LinkedList<Integer>();
    }

    /* To calculate the maximum value of this feature */
    public void calcMaxValue() {
        int max=0;
        for(int value:values){
            max = (value > max) ? value : max;
        }
        this.max_value = max;
    }
}