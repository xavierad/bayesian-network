package bnc;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Data
 */
public class Data {

    LinkedList<String[]> data = new LinkedList<String[]>();;

    public Data(LinkedList<String[]> data) {
        this.data = data;
    }

    public int getNumberOfFeatures(){
        return data.getFirst().length;
    }

    public int getDataSize() {
        return data.size();
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