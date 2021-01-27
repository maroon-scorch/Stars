package edu.brown.cs.student.stars;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NaiveRadius implements Command{
    private ArrayList<Star> starsList;
    private StringBuilder currentFile;
    private ArgValidator validator = new ArgValidator();
    private final Number[] acceptArgs = {2, 4};

    public NaiveRadius(ArrayList<Star> starsList, StringBuilder currentFile) {
        this.starsList = starsList;
        this.currentFile = currentFile;
    }

    public void execute(ArrayList<String> args) {
        int argSize = args.size();
        if (!areArgsValid(args)) {
            return;
        }

        switch(argSize) {
            case 2:
                double radius = Double.parseDouble(args.get(0));
                String sName = args.get(1);
                String sStarNoQuotes = sName.substring(1, sName.length() - 1);
                ArrayList<Star> starsInRange2 = performNaiveRadius(radius, sStarNoQuotes, starsList);
                starsInRange2.forEach(System.out::println);
                break;
            case 4:
                double dRadius = Double.parseDouble(args.get(0));
                double dPosX = Double.parseDouble(args.get(1));
                double dPosY = Double.parseDouble(args.get(2));
                double dPosZ = Double.parseDouble(args.get(3));
                ArrayList<Star> starsInRange4 = performNaiveRadius(dRadius, dPosX, dPosY, dPosZ, starsList);
                starsInRange4.forEach(System.out::println);
                break;
        }

        // System.out.println(starsList.size());
    }

    public ArrayList<Star> performNaiveRadius(double r, String name, ArrayList<Star> alos) {
        Optional<Star> selectedStar = findStarWithName(name);

        if (selectedStar.isEmpty()) {
            return new ArrayList<>();
        }
        else {
            Star presentStar = selectedStar.get();

            double selectedX = presentStar.getX();
            double selectedY = presentStar.getY();
            double selectedZ = presentStar.getZ();

            ArrayList<Star> template = copyWithType(alos);
            template.removeIf(star -> star.getName().equals(name));

            return performNaiveRadius(r, selectedX, selectedY, selectedZ, template);
        }
    }

    public ArrayList<Star> performNaiveRadius(double r, double x, double y, double z, ArrayList<Star> alos) {
        if (r == 0) {
            return findStarsWithCord(x, y, z);
        }

        // Ask TA for clone vs copyWithType
        ArrayList<Star> template = copyWithType(alos);
        template.removeIf(star -> (star.distanceTo(x, y, z) > r));
        Collections.sort(template, (star1, star2)
                -> Double.compare(star1.distanceTo(x, y, z), star2.distanceTo(x, y, z)));
        return template;
    }

    private ArrayList<Star> findStarsWithCord(double x, double y, double z) {
        ArrayList<Star> template = new ArrayList<Star>();
        for(Star eachStar : starsList) {
            if ((x == eachStar.getX()) && (y == eachStar.getY()) && (z == eachStar.getZ())) {
                template.add(eachStar);
            }
        }
        return template;
    }

    private Optional<Star> findStarWithName(String name) {
        for(Star eachStar : starsList) {
            if (eachStar.getName().equals(name)) {
                return Optional.of(eachStar);
            }
        }
        return Optional.empty();
    }

    private <A> ArrayList<A> copyWithType(ArrayList<A> aloa) {
        return new ArrayList<>(aloa);
    }

    public boolean areArgsValid(ArrayList<String> args) {
        int argSize = args.size();
        boolean result = true;

        // Testing if the Stars CSV File has been loaded yet - NEED TO CORRECT LATER
        if (currentFile.length() == 0) {
            System.out.println("ERROR: No file has been loaded yet");
            return false;
        }

        // Testing for Valid Args Size
        if (!Arrays.asList(acceptArgs).contains(argSize)) {
            System.out.println("ERROR: Incorrect Number of Arguments for \"naive_radius\"");
            return false;
        }

        // Testing for Valid Radius - Must be Numeric and >= 0
        String sRadius = args.get(0);
        if (validator.isArgNumeric(sRadius)) {
            double dRadius = Double.parseDouble(sRadius);
            if (dRadius < 0) {
                result = false;
                System.out.println("ERROR: Radius Cannot Be Negative");
            }
        } else {
            result = false;
            System.out.println("ERROR: Radius is Not a Number");
        }

        // If taking in 2 Arguments, check if the second argument is a valid String and exists in the CSV
        if (argSize == 2) {
            String sName = args.get(1);
            if (!validator.isArgString(sName)) {
                result = false;
                System.out.println("ERROR: Name is Not Surrounded by Double Quotes");
            }
            else {
                String starNoQuotes = sName.substring(1, sName.length() - 1);
                Optional<Star> selectedStar = findStarWithName(starNoQuotes);
                if (selectedStar.isEmpty()) {
                    result = false;
                    System.out.printf("ERROR: The Selected Star %s is Not Found\n", sName);
                }
            }
        }

        // If taking in 4 Arguments, check if the rest of the 3 arguments are all Numeric
        if (argSize == 4) {
            String sXPos = args.get(1);
            String sYPos = args.get(2);
            String sZPos = args.get(3);

            if (!(validator.isArgNumeric(sXPos) && validator.isArgNumeric(sYPos) && validator.isArgNumeric(sZPos))) {
                result = false;
                System.out.println("ERROR: X, Y, and Z Coordinates Should All Be Numeric");
            }
        }

        return result;
    }
}
