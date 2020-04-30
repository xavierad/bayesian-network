package main;


import bnc.Data;
import load_data.ReadCSV;

/* ****************************************************************************************
 * Para compilar: >/src javac bnc/*.java load_data/*.java main/*.java 
 * Para correr: >/src java main.Main bias-train.csv bias-test.csv <score>
 * Para gerar o ficheiro .jarpara gerar fichero jar
 * - jar cfm lab3.jar manif.txt lab3/* main/*
 * - java -jar hello.jar   --> para compilar o .jar
 * - jar tf hello.jar      --> para ver o conteúdo 
 * 
 * 
 * DÚVIDAS:
 * []
 * []
 * 
 * 
 * 
 * A FAZER:
 * []
 * []
 * []
 * *****************************************************************************************/



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

        System.out.println("Train Data: \n" + train_data);
        System.out.println("Test Data: \n" + test_data);
        
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