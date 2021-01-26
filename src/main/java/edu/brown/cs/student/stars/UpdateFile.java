package edu.brown.cs.student.stars;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;

public class UpdateFile implements Command{
    private ArrayList<Star> starsList;
    private boolean isSuccessful = false;
    private Number[] acceptArgs = {1};

    public UpdateFile(ArrayList<Star> starsList) {
        this.starsList = starsList;
    }

    public void execute(ArrayList<String> args) {
        if (areArgsValid(args)) {
            String filepath = args.get(0);
            changeFile(filepath);
            if (isSuccessful)
                System.out.printf("Read %d stars from %s\n", starsList.size(), filepath);
            isSuccessful = false;
        }
    }

    public boolean areArgsValid(ArrayList<String> args) {
        int argSize = args.size();
        if (!Arrays.asList(acceptArgs).contains(argSize)) {
            System.out.println("ERROR: Incorrect Number of Arguments for \"star\"");
            return false;
        }

        return true;
    }

    public void changeFile(String filepath) {
        starsList.clear();
        try (BufferedReader csvReader = new BufferedReader(new FileReader(filepath))) {
            csvReader.readLine();
            String line;
            while ((line = csvReader.readLine()) != null) {
                starsList.add(lineToStar(line));
            }
            isSuccessful = true;
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File does not exist.");
        } catch (IOException e) {
            System.out.println("ERROR: File Name/Path/Content is not valid");
        }
    }

    private Star lineToStar(String line) {
        // TODO: Convert line to JSON to be more scalable

        String[] starArgsArray = line.split(",");

        return new Star(
                starArgsArray[0],
                starArgsArray[1],
                Double.parseDouble(starArgsArray[2]),
                Double.parseDouble(starArgsArray[3]),
                Double.parseDouble(starArgsArray[4]));
    }
}
