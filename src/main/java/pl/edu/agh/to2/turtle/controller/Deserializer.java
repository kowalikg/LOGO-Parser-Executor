package pl.edu.agh.to2.turtle.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Deserializer {
    public List deserialize(File file) {
        List commands = new ArrayList<>();
        FileReader fileReader = null;
        String line;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        try {
            while ((line = bufferedReader.readLine()) != null) {
                commands.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commands;
    }
}
