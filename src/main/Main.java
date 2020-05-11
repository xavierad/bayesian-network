package main;

import data.*;
import load_data.*;
import classifier.*;


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
        /*
        if(args[2].equals("LL")) {
            ICostFunction cf = new LL();
            System.out.println("Score chosen: LL");
        }
        else if(args[2].equals("MDL")) {
            ICostFunction cf = new MDL();
            System.out.println("Score chosen: MDL");
        }
        else {
            System.out.println("No score chosen!");
            System.exit(1);
        }*/


        ReadCSV read_train = new ReadCSV(args[0]);
        Dataset train_data = new Dataset(read_train.ReadFile());
        ReadCSV read_test = new ReadCSV(args[1]);
        Dataset test_data = new Dataset(read_test.ReadFile());

       /* System.out.println("Train Data: \n" + train_data);
        System.out.println("Test Data: \n" + test_data);*/

        // Possível estrutura para o main - mutável
        ICostFunction cf = new MDL();
        IClassifier bnc = new BNC(cf);
        long start = System.currentTimeMillis();
        bnc.build(train_data);
        long buildTime = System.currentTimeMillis() - start;


        int Nt = test_data.getDataSize();
        int nt = test_data.getRVDimension()-1;

        int[] pred = new int[Nt];
        start = System.currentTimeMillis();
        pred = bnc.predict(test_data);
        long predictTime = System.currentTimeMillis() - start;

        /** PRINTS */
        //System.out.print(bnc.StructuretoString());
        System.out.print("Time to build: " + buildTime + " ms\n");
        for (int i = 0; i < pred.length; i++)
          System.out.format("instance %d: %d\n", i, pred[i]);
        System.out.print("Time to predict: " + predictTime + " ms\n");
        System.out.print(new ClassifierMetrics(pred, test_data.getRVariable(nt).getValues()));

    }
}
