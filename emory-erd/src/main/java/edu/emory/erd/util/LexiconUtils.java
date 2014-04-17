package edu.emory.erd.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

public class LexiconUtils {

  public LexiconUtils() {
    // TODO Auto-generated constructor stub
  }
  
  public static void lexiconMatching(String targetFilePath, String inputFilePath, String outputFilePath) throws IOException {

    String line;
    HashSet<String> lexiconFreebaseID = new HashSet<String>();
    
    /**
     * Creating a buffered reader to read the file
     */
    BufferedReader bReader = new BufferedReader(
            new FileReader(targetFilePath));
    /**
     * Looping the read block until all lines in the file are read.
     */
    while ((line = bReader.readLine()) != null) {

        /**
         * Splitting the content of tab separated line
         */
        String datavalue[] = line.split("\t");
        String freebaseID = datavalue[0];
        lexiconFreebaseID.add(freebaseID);

        /**
         * Printing the value read from file to the console
         */
        //System.out.println(value1 + "\t" + value2 + "\t" + value3 + "\t" + value4);
        
    }
    bReader.close();
    
    bReader = new BufferedReader(
            new FileReader(inputFilePath));
    int numMissingEntities = 0;
    int numMatchedEntities = 0;
    FileWriter fos = new FileWriter(outputFilePath);
    PrintWriter dos = new PrintWriter(fos);
    try {
      while ((line = bReader.readLine()) != null) {
        
        String datavalue[] = line.split("\t");
        if (!lexiconFreebaseID.contains(datavalue[0])){
          numMissingEntities++;
          dos.println(line);
          //System.out.println(line + " seems not existing in the lexcon");
          //System.out.println(datavalue[0] + " "+ datavalue[1] + " seems not existing in the lexcon");
        } else{
          numMatchedEntities++;
        }
    
      }
    } catch (IOException e) {
      System.out.println("Error Printing Tab Delimited File");
    }
    
    System.out.println("numMissingEntities = " + numMissingEntities);
    System.out.println("numMatchedEntities = " + numMatchedEntities);
    System.out.println("numMissedEntities stored in " + outputFilePath);
    
    bReader.close();
    dos.close();
    fos.close();
    
  }
  
  public static void main(String[] args) throws IOException {
    try {
      //for (String inputFile : args) {
      lexiconMatching(args[0], args[1], args[2]); // args[0] = targetFilePath; args[1] = inputFilePath; args[2] = outputFilePath
      //}
    } catch (IOException exc) {
        System.err.println(exc.getMessage());
    }
    
}

}
