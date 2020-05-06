package load_data;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
                for(String d:data) {
                    try {
                        Integer.parseInt(d);            
                    } catch (NumberFormatException e) {
                        System.out.println("Got an unusual instance in file " + file + '!');
                        System.exit(1);
                    }
                }
                tdata.add(data);                
            }  
            br.close();
        }

        catch (IOException e) {  
            e.printStackTrace();  
        }

        return tdata;
    }


           

}