package ContactsManager;

/*
 * Name: Harsha Deep Reddy V
 * 
 * Date: 13 Sep 2014 
 * Purpose of this module: The controller file for the project. This handles the file IO for the project.
 *                         The File IO is intentionally kept separate from the UI (View sections) of the project.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {
    //Read data from the text file
    public static void readFile()
    {
        try
        {
        //Use buffered reader and file reader. 
        BufferedReader br = new BufferedReader(new FileReader("data.txt"));
        
        String lineRead = "";
        ContactsView_UI_Backend.data.clear();
        //Read each line and split them as they tehy are tab separated.
        //Add them into the data list
        while((lineRead = br.readLine()) != null)
        {
            String[] dataItems = lineRead.split("\t");
            
            ContactsView_UI_Backend.data.add(new Person(dataItems[0], dataItems[1],dataItems[2],dataItems[3],
                    dataItems[4], dataItems[5],dataItems[6],dataItems[7],dataItems[8],dataItems[9],
                    dataItems[10],dataItems[11]));
        }
        }
        catch(FileNotFoundException fnfe)
        {
            System.err.println("IOException: " + fnfe.getMessage());
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
        
    }
    
    //Write data to the text file
    public static void writeToFile()
    {
        try
        {
            //Use File Write to overwrite the file everytime this method is called
            String filename= "data.txt";
            FileWriter fw = new FileWriter(filename,false);
            
            //Write each object from data list.
            //Write them into the file
            for(Person person: ContactsView_UI_Backend.data)
            {
                String lineToWrite = person.getFirstName() + "\t" +
                        person.getLastName() + "\t" +
                        person.getMiddleInitial() + "\t" +
                        person.getAddressLine1() + "\t" +
                        person.getAddressLine2()+ "\t" +
                        person.getCity()+ "\t" +
                        person.getState()+ "\t" +
                        person.getCountry()+ "\t" +
                        person.getZipcode()+ "\t" +
                        person.getPhoneNumber()+ "\t" +
                        person.getEmail()+ "\t" +
                        person.getGender()+ "\n" ;
                
                fw.write(lineToWrite);
            }
            
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}
