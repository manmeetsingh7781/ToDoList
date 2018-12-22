/*
* This file was written in 2018 by Manmeet Singh
* This is my Second project of java
*
* First one was Guess the word game
* And this is the ToDO list Project
*
* */



package com.example.honeysingh.myapplication;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


// Main Thread
public class Create_List {

    // These are the variables so we do not have to re-declare it again and again
    private static String fileName= "my_list_file.txt";
    private static File myFile = new File(fileName);
    private static Scanner reader;

    // Static Scanner so we do not have to declare every time
    static {
        try {
            reader = new Scanner(myFile);
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    }

    // Constructor for creating a file if does not exists
    Create_List(){
        try {
            // Creates a new file if not exists
            if (!myFile.exists()) {

                PrintWriter file = new PrintWriter(fileName);

                // Writes a file with defaults headers
                file.write("Index\tItem\t\tDate\t\tTime");

                // Prints empty lines
                file.println();
                file.println();

                // Forces the text to be written in the file
                file.flush();

            }
        }
        catch (FileNotFoundException e){e.printStackTrace();}
    }



    // Create a Todo list item hahah lol why is this blue tho
    @RequiresApi(api = Build.VERSION_CODES.O)
    String make_item(String item)
    {
        // Checks if the file is exists in the Current working dict
        if(myFile.exists()) {
            // Adding a item in the list
            try {
                // Using FileWriter in the BufferedWriter so we can avoid overwriting the file data :: we used append=true parameter in the      FileWriter constructor
                BufferedWriter reader = new BufferedWriter(new FileWriter(myFile, true));

                // Appending the data into the existing file
                String one_item =   lineCounter() +"\t"+  item;
                String two_time =  time();
                // Adding data into the file
                reader.append(one_item);
                reader.append(two_time);

                // Entering new Line after each item has been added in the list
                reader.newLine();

                // Forcing the data to be flushed into the file
                reader.flush();

                // Returns what was added
                return item+" has been added";

            }catch (FileNotFoundException e){
                return e.toString();
            }
                catch (IOException e) {
                    return e.toString();
            }
            // Else this will create a new file
        }else {
            createAFile();
            return("File does not Exists\nCreating a New file\nFile has been Created... \nPlease re run the task\nExiting...");
        }

    }

    // Gets the current Date and time
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String time(){
        // This may create a NullPointer so we are trying to catch it
        try {
            // Get the Current Date and time using the DateTimeFormatter get the right pattern and get time using LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("\t\tdd/MM/yyyy \tHH:mm:ss");

            // Getting current time
            LocalDateTime now = LocalDateTime.now();

            // We are changing the format of DateTime
            return formatter.format(now);
        }
        catch (NullPointerException e){e.printStackTrace(); return null;}
        }


    // This is same as constructor.... We declared it here because we had to use it in the function
    private void createAFile(){
        try {
            if (!myFile.exists()) {

                // THis creates a new file into the working dictionary
                PrintWriter file = new PrintWriter(fileName);

                // Writing header into the file after it's been created
                file.write("Index\tItem\t\tDate\t\tTime");

                // Printing two blank lines
                file.println();
                file.println();

                // Flush forces the file to be written
                file.flush();
            }
        }
        catch (FileNotFoundException e){e.printStackTrace();}
    }


    // This will read the file
    public String reader() {
        try {
            // This is take and read the file
            Scanner reader = new Scanner(myFile);
            String data = "";
            if(reader.hasNext()) {
                // Read each line one by one
                do {
                    data += reader.nextLine();
                    if (reader.hasNextLine()) {
                        data += "\n";
                    }
                }
                // Until next line exists
                while (reader.hasNext());
                return data;
            } else {
                return "Line Counter Could not able to read the line\nPlease add some Task in the List and Make Sure file exists";
            }
            // Else Catch an error
        } catch (FileNotFoundException e) {
           return e.toString();
        }
    }

    // Counts the number of lines
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String  lineCounter() {
        // -1 because of the headers and the next empty line so our list will starts at 1
        Integer counter = -1;
        while ( reader.hasNext() ) {
            // While it has lines it will add 1  into the counter
                reader.nextLine();
                counter++;
        }
        // Returns the number of lines
        return counter.toString();
    }

    // Here we are finding an element and this will return the list of the item
     ArrayList  findItem(String item) {

        // Storing each element it will find
         ArrayList<String> result = new ArrayList<>();

         // Starts reading the file
         while (reader.hasNextLine()) {

             // Saving each line into the array
             String flag = reader.nextLine();

             // Checking the flag and item is equal so it will return if found
             if (flag.toLowerCase().contains(item.toLowerCase())) {
                 result.add(flag);
             }
         }

         // In the end it will return the list of results it has stored in it which means data of null lol
         return result;
     }

     // Removes the item from the list
     String removeItem(String item) {
         try {
             // A backup to hold all the data from the file into the Array
             ArrayList<String> result = new ArrayList<>();

             // Reading the file
             Scanner read = new Scanner(myFile);

             // While reading the file
             while(read.hasNext()){

                 // We are saving each line into the String
                 String flag = read.nextLine();

                 // Pushing each line into the Result which is our backup
                 result.add(flag);

                 // Here we are using lowercase in-case user miss capitalize their words
                 // We are also checking if the flag contains our item
                 if(flag.toLowerCase().contains(item.toLowerCase())) {

                     // Enhanced loop does not work in a way like if we want to remove an element so we used for-loop
                     for(int x = 0; x < result.size(); x++){

                         // Here we are check if the elements are equal
                         if(flag.toLowerCase().equals(result.get(x).toLowerCase())){

                             // If they are equal then we can remove it
                             result.remove(flag);
                         }
                     }
                 }
             }
             // At this point we are done will adding, removing and backing-up our file || now its time to write it
             try {

                 // We are using BufferedWriter and FileWriter so we can append the file without removing old data from it
                 BufferedWriter writer = new BufferedWriter(new FileWriter(myFile));

                 // Here we are writing each element from our backup into the file
                 for(String each: result){

                     // Appending a line
                     writer.append(each);

                     // New line after ever line appends
                     writer.newLine();

                     // Use flush to force write the elements into the file
                     writer.flush();
                 }

                 // Catching any Exceptions may occur while process
             } catch (IOException e) {
                 return  e.toString();
             }
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

        // In the end of process it will return null
        return null;
     }


    // Here we are updating our item in the list using [What to replace] with new to replace
    @RequiresApi(api = Build.VERSION_CODES.O)
    void updateItem(String which_item_to_replace, String new_item) {


        // First we will remove the old item
        removeItem(which_item_to_replace);

        // Then we will add the new item
        make_item(new_item);
    }


}
