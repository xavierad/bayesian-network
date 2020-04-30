package bnc;

import java.util.LinkedList;

/**
 * Data
 */
public class Data {

    LinkedList<String[]> data;

    public Data(LinkedList<String[]> data) {
        this.data = data;
    }

    public int getNumberOfFeatures(){
        return data.getFirst().length;
    }

    public int getDataSize() {
        return data.size();
    }    
}