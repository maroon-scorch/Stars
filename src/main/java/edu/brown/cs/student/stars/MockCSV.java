package edu.brown.cs.student.stars;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import java.text.SimpleDateFormat;
import java.text.ParseException;

public class MockCSV implements Command{
    private final ArrayList<MockPerson> peopleList = new ArrayList<>();

    private final Number[] acceptArgs = {1};
    private final CSVParser parser = new CSVParser();
    private final String[] expectedHeaders
            = {"FirstName", "LastName", "DateTime", "Email", "Gender", "StreetAddress"};

    public MockCSV() { }

    public void execute(ArrayList<String> args) {
        if (areArgsValid(args)) {
            String filepath = args.get(0);
            boolean isSuccessful = parser.parse(filepath, peopleList, expectedHeaders, this::lineToPerson);
            if (isSuccessful) {
                System.out.println("Success!");
            }
        }
    }

    public boolean areArgsValid(ArrayList<String> args) {
        int argSize = args.size();
        if (!Arrays.asList(acceptArgs).contains(argSize)) {
            System.out.println("ERROR: Incorrect Number of Arguments for \"mock <csv>\"");
            return false;
        }
        return true;
    }

    public MockPerson lineToPerson(String line) {
        String[] personAttributes = line.split(",");

        String firstName = personAttributes[0];
        String lastName = personAttributes[1];

        String date = personAttributes[2];



        String gender = personAttributes[4];

        return new MockPerson("","","","");
    }


}
