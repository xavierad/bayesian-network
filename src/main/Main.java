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
 * [] ver linha 36 de Dataset: parece que faz duas vezes o system.out.println...
 * [] talvez seja desnecessário guardar o atributo data em dataset: getRVDimension seria random_vector.length,
 *     getDataSize seria random_vector[0].size() e na linha 27 pôr this.random_vector = new RVariable[data.getFirst().length];
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
        ICostFunction cf;
        if(args[2].equals("LL"))
            cf = new LL();
        else if(args[2].equals("MDL"))
            cf = new MDL();
        else {
            System.out.println("Invalid Score Method!");
            cf = null;
            System.exit(1);
        }


        ReadCSV read_train = new ReadCSV(args[0]);
        Dataset train_data = new Dataset(read_train.ReadFile());
        ReadCSV read_test = new ReadCSV(args[1]);
        Dataset test_data = new Dataset(read_test.ReadFile());

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
        //System.out.print("Time to build: " + buildTime + " ms\n");
        System.out.format("%-20s%s\n",("Time to build: "), (buildTime + " ms"));
        for (int i = 0; i < pred.length; i++)
          System.out.format("%-20s%d\n",("-> instance " + i + ":"), pred[i]);
        System.out.format("%-20s%s\n",("Time to predict: "), (predictTime + " ms"));
        System.out.format("%-20s%s\n", ("Resume: "), new ClassifierMetrics(pred, test_data.getRVariable(nt).getValues()));

    }
}
