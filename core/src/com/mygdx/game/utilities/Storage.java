package com.mygdx.game.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

//the storage is a file
public class Storage {
    private static Storage _instance;
    private static String fileName;
    private static File file;
    private static FileReader fileReader;
    private static FileWriter fileWriter;
    private static BufferedWriter bfWriter;
    private static BufferedReader bfReader;

    //does not create new file if the file already exists
    protected Storage(String fileName) {
        this.fileName = fileName;
        file = new File(fileName);
        createFile();
    }

    public static Storage getInstance()
    {
        if (_instance == null)
            _instance = new Storage("core/assets/Scores.txt");
        return _instance;
    }

    //store in decreasing order of score
    public static void store(String userName, int score)
    {
        Vector<String> content = addEntry(userName, score);
        try {
            fileWriter = new FileWriter(fileName);
            for (String str: content)
                fileWriter.write(str);
            fileWriter.close();
        } catch (IOException e)
        {
            System.out.println("Failed to store score");
            System.out.println(e);
        }
    }

    //e.g. get top 3 entries/scores
    public static String getTopEntry(int numTopEntries)
    {
        int i = 1;
        String returnString = new String();
        Vector<String> content = getTopEntries(numTopEntries);

        for (String str: content)
        {
            returnString += "No. " + i + "  " + str;
            i++;
        }
        return returnString;
    }

    private static Vector<String> getTopEntries(int numTopEntries)
    {
        String entry;
        Vector<String> content = new Vector<String>();

        try {
            bfReader = new BufferedReader(
                    new FileReader(fileName));
            while ((entry = bfReader.readLine()) != null && content.size()<numTopEntries) {
                if (entry.valueOf(entry.length()-1) != "\r\n")
                    entry = entry + "\r\n";
                content.add(entry);
            }
            bfReader.close();
        } catch (IOException e)
        {
            System.out.println(e);
        }
        return content;
    }

    //ranking starts from 1
    public static int getRanking(int score)
    {
        int index = 0;
        Vector<String> content = getContent();
        int tempScore;

        for (index = 0; index < content.size(); index++)
        {
            tempScore = Integer.parseInt(content.elementAt(index).split(":")[0]);
            if (tempScore <= score)
            {
                return index+1;
            }
        }

        return index+1;
    }

    //return score-decreasing sorted content of storage after adding new entry
    private static Vector<String> addEntry(String username, int score)
    {
        int index = 0;
        Vector<String> content = getContent();
        int tempScore;
        //add entry
        for (index = 0; index < content.size(); index++)
        {
            tempScore = Integer.parseInt(content.elementAt(index).split(":")[0]);
            if (tempScore <= score)
            {
                break;
            }
        }

        content.add(index, score + ":" + username + "\r\n");

        return content;
    }

    private static Vector<String> getContent()
    {
        String entry;
        Vector<String> content = new Vector<String>();
        try {
            bfReader = new BufferedReader(
                    new FileReader(fileName));
            while ((entry = bfReader.readLine()) != null) {
                if (entry.valueOf(entry.length()-1) != "\r\n")
                    entry = entry + "\r\n";
                content.add(entry);
            }
            bfReader.close();
        } catch (IOException e)
        {
            System.out.println(e);
        }
        return content;
    }

    private static void createFile()
    {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
