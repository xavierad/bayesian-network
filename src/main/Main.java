package main;

import classifier.*;
import load_data.*;
import metrics.ClassifierMetrics;

/* ****************************************************************************************
 * (Xavier)
 * Para compilar: >/src javac bnc/*.java load_data/*.java main/*.java
 * Para correr: >/src java main.Main bias-train.csv bias-test.csv <score>
 * Para gerar o ficheiro .jarpara gerar fichero jar
 * - jar cfm lab3.jar manif.txt lab3/* main/*
 * - java -jar hello.jar   --> para compilar o .jar
 * - jar tf hello.jar      --> para ver o conteúdo
 *
 * Para compilar: (Vicente)
 *   /. javac @files.txt  (files.txt contêm os path para os varios .java este é atualizado via
 *                         dir /b /s | findstr \.java > files.txt )
 * Para correr:
 *  /src java main/Main ../bin/bias-train.csv ../bin/bias-test.csv LL
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
            System.out.println("Expected 3 arguments instead of " + args.length + ".");
            System.exit(1);
        }

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

        ReadCSV read_train = new ReadCSV(args[0]);
        Dataset train_data = new Dataset(read_train.ReadFile());
        ReadCSV read_test = new ReadCSV(args[1]);
        Dataset test_data = new Dataset(read_test.ReadFile());

       /* System.out.println("Train Data: \n" + train_data);
        System.out.println("Test Data: \n" + test_data);*/

        /* Possível estrutura para o main - mutável

        if(args[2].equals("LL")) {
            ICostFunction cf = new LL;
            System.out.println("Score chosen: LL");
        }
        else if(args[2].equals("MDL")) {
            ICostFunction cf = new MDL;
            System.out.println("Score chosen: MDL");
        }
        else {
            System.out.println("No score chosen!");
            System.exit(1);
        }

        ReadCSV read_train = new ReadCSV(args[0]);
        Dataset train_data = new Dataset(read_train.ReadFile());
        ReadCSV read_test = new ReadCSV(args[1]);
        Dataset test_data = new Dataset(read_test.ReadFile());

        IClassifier bnc = new BNC(cf);
        long start = System.currentTimeMillis();
        bnc.build(train_data);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        new int[] classes_predicted = bnc.predict(test_data);

        scores.....
        */
    }
}
