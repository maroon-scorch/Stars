package edu.brown.cs.student.stars;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import java.text.SimpleDateFormat;

public class MockPerson {

    private String firstName, lastName, gender;
    private Date date;
    public MockPerson(String firstName, String lastName, String dateTime, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//        try {
//            this.date = formatter.parse(dateTime);
//        } except (java.text.ParseException e) {
//            System.out.println();
//        }
        this.gender = gender;
    }

//    public Optional<Date> stringToDate(String str) {
//        if (str.isEmpty()) {
//            return Optional.empty();
//        }
//
//        try {
//            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(str);
//            return Optional.of(date);
//        } catch (ParseException e) {
//            System.out.println("ERROR: Invalid Format of Date, please check if your dates are in the form MM/dd/yyyy");
//        }
//    }

    public String toString() {
        return "";
    }
}
