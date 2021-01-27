package edu.brown.cs.student.stars;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Arrays;
import java.util.ArrayList;

interface LineConverter< T > {
    T accept(String line);
}

public class CSVParser {

    public CSVParser() { }

    public < T > boolean parse(
            String filepath,
            ArrayList<T> template,
            String[] expectedHeaders,
            LineConverter< T > lineConverter) {

        template.clear();
        try (BufferedReader csvReader = new BufferedReader(new FileReader(filepath))) {
            String[] headers = csvReader.readLine().split(",");
            if (!Arrays.equals(headers, expectedHeaders)) {
                System.out.println("ERROR: The headers of the File are not the expected headers.");
                return false;
            }
            String line;
            while ((line = csvReader.readLine()) != null) {
                template.add(lineConverter.accept(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File does not exist.");
            return false;
        } catch (IOException e) {
            System.out.println("ERROR: File Name/Path/Content is not valid");
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: Unable to parse file, please check if content is formatted properly.");
            return false;
        }
        return true;
    }
}
