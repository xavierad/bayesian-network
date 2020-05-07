package load_data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * ReadCSV - This class will provide rowed data 
 * read from a CSV file. It throws away the 
 * first row (the header).
 */
public class ReadCSV {

    /** The name of the file to be read */
    String file = new String();

    /**
     * This the constructor of ReadCSV class. 
     * Assigns the string file to be read to 
     * the attribute file.
     * @param file file to be read.
     */
    public ReadCSV(String file) {
        this.file = file;
    }        

    /**
     * This method will read a file with BufferedReader library. 
     * It will abort the program if there is some unusal 
     * type of data like a non-integer.  
     * @return tdata (the rowed data read).
     */
    public LinkedList<String[]> ReadFile() {
        
        /**Data structure that will contain in rows the file's data */
        LinkedList<String[]> tdata = new LinkedList<String[]>(); 
        
        /* Reading the files */
        try {     
                
            String line = "";  

            /* Data loading */
            BufferedReader br = new BufferedReader(new FileReader(file));  
            line = br.readLine(); // first line are only strings, throwing it away
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