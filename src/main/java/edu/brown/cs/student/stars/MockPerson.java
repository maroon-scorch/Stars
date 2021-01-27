package edu.brown.cs.student.stars;

import java.util.Date;
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

    public String toString() {
        return "";
    }
}
