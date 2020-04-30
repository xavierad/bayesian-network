package main;

import java.util.LinkedList;

import bnc.Data;
import bnc.TrainData;
import load_data.ReadCSV;

/* DEPOIS DE TUDO APAGAR OS System.out.prinln AUXILIARES! */



public class Main {
    public static void main(String[] args) {

        if(args.length != 3) {
            System.out.println("Number of arguments expected is 3!");
            System.exit(1);
        }

        ReadCSV read_train = new ReadCSV(args[0]);
        Data train_data = new Data(read_train.ReadFile());
        ReadCSV read_test = new ReadCSV(args[1]);        
        Data test_data = new Data(read_test.ReadFile());
        
        /* Score strategy */
        if(args[2].equals("LL")) {
            //strategy LL
            System.out.println("Score chosen: LL");
        }
        else if(args[2].equals("MDL")) {
            //strategy MDL
            System.out.println("Score chosen: MDL");
        }
        else {
            System.out.println("No score chosen!");
            System.exit(1);
        }
    }
}