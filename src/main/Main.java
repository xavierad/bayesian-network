package main;

import bnc.DataSet;
import load_data.ReadCSV;

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
 * [] Ver no construtor de DataSet.java
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

        ReadCSV read_train = new ReadCSV(args[0]);
        DataSet train_data = new DataSet(read_train.ReadFile());
        ReadCSV read_test = new ReadCSV(args[1]);        
        DataSet test_data = new DataSet(read_test.ReadFile());

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
	
	/** Structure Learning **/
	/* Process data - into Nijkc and maybe seperate Xi */

	/* Get Weigths - acoording to the model chosen */
	
	/* Build Weighted Graph */

	/* Transform to MaxSpanningTree (Undirected) */

	/* Transform to Directed Tree - arbitearly chosen root */

	/* (?) Add leafs to tree with C values */

	/** Parameter Learning (or just BNC) **/
	/* Get OFE */

	/* Get ClassOFE */

	/* Predict (?)*/

	/** OU
	    Classifer example = new BayesNetworkClassifierUsingLL(train_data)
	    (...)
	    results = example.predict(test_data);
	 ** OU ainda
	    Classifier example = new BayesNetworkClassifier(LLcost);
	    Classifier example = new BayesNetworkClassifier(MDLcost);
	    
	    interface ICostFunction {
	       
	    }
	*/
    }
}
