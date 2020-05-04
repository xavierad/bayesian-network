package bnc;

/**
 * LL
 */
public class LL implements IModSelection {

    DataSet train_data, test_data;

    public LL(DataSet train_data, DataSet test_data) {
        this.train_data = train_data;
        this.test_data = test_data;
    }        

    public void allCountings() {

    }

    public void getWeights() {
        int n = train_data.getRVDimension()-1, qi, ri, s;
        int weights[][] = new int[n][n];

        for(int i=0; i<n-1; i++) {
            for(int _i=i+1; i<n; i++) {
                qi=train_data.random_vector[i+1].max_value;;

                for(int j=1; j<=qi ; j++) {                
                    ri=train_data.random_vector[i].max_value;

                    for(int k=1; k<=ri; k++) {                
                        s=train_data.random_vector[n+1].max_value;

                        for(int c=1; c<=s; c++) {
                            weights[i][_i] = 1;
                        }
                    }
                }
            }           
        }
    }

    public void createCompletedGraph() {
        int size = train_data.getRVDimension();
        int graph[][] = new int[size][size];

    }


    @Override
    public void score() {

    }
}