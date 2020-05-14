package main;

import data.*;
import load_data.*;
import classifier.*;
import metrics.*;

public class Main {
    public static void main(String[] args) {

        if(args.length != 3) {
            System.out.println("Expected 3 arguments instead of " + args.length + ".");
            System.exit(1);
        }
        IScoreFunction sf;
        if(args[2].equals("LL"))
            sf = new LL();
        else if(args[2].equals("MDL"))
            sf = new MDL();
        else {
            System.out.println("Invalid Score Method!");
            sf = null;
            System.exit(1);
        }

        // Reading and pre-processing both files for training and testing
        ReadCSV read_train = new ReadCSV(args[0]);
        Dataset train_data = new Dataset(read_train.ReadFile());
        ReadCSV read_test = new ReadCSV(args[1]);
        Dataset test_data = new Dataset(read_test.ReadFile());

        train_data.getRVariable(train_data.getRVDimension()-1).getMax_value();

        // Build a classifier with cost function sf
        IClassifier tan = new TAN(sf);
        long start = System.currentTimeMillis();
        tan.build(train_data);
        long buildTime = System.currentTimeMillis() - start;

        int Nt = test_data.getDataSize();
        int nt = test_data.getRVDimension()-1;

        // Making the predictions with test data
        int[] pred = new int[Nt];
        start = System.currentTimeMillis();
        pred = tan.predict(test_data);
        long predictTime = System.currentTimeMillis() - start;

        // Prints of the results: classifier structure network, predictions, and the correspondent timers, and resume of metrics
        System.out.print(tan);
        System.out.format("%-20s%s\n",("Time to build: "), (buildTime + " ms"));
        for (int i = 0; i < pred.length; i++)
            System.out.format("%-20s%d\n",("-> instance " + (i+1) + ":"), pred[i]);
        System.out.format("%-20s%s\n",("Time to predict: "), (predictTime + " ms"));
        System.out.format("%-20s%s\n", ("Resume: "), new ClassifierMetrics(pred, test_data.getRVariable(nt).getValues()));

    }
}
