package load_data;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * ReadCSV
 */
public class ReadCSV {

    String file = new String();

    public ReadCSV(String file) {
        this.file = file;
    }        

    public LinkedList<String[]> ReadFile() {
        
        LinkedList<String[]> tdata = new LinkedList<String[]>(); 
        
        /* Reading the files */
        try {     
                
            String line = "";  

            /* Train data loading */
            BufferedReader br = new BufferedReader(new FileReader(file));  
            line = br.readLine(); // first line are only strings
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // use comma as separator
                tdata.add(data);                
            }  
            br.close();
            //tdata.forEach(td -> System.out.println(Arrays.toString(td)));
        }

        catch (IOException e) {  
            e.printStackTrace();  
        }

        return tdata;
    }


           

}