package edu.brown.cs.student.stars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

public class NaiveNeighbors implements Command{
    private ArrayList<Star> starsList;
    private ArgValidator validator = new ArgValidator();
    private final Number[] acceptArgs = {2, 4};
    public NaiveNeighbors(ArrayList<Star> starsList) {
        this.starsList = starsList;
    }

    public void execute(ArrayList<String> args) {
        int argSize = args.size();
        if (!areArgsValid(args)) {
            return;
        }

        switch(argSize) {
            case 2:
                int neighbors = Integer.parseInt(args.get(0));
                String sName = args.get(1);
                String sStarNoQuotes = sName.substring(1, sName.length() - 1);
                ArrayList<Star> starsInRange2 = performNaiveNeighbors(neighbors, sStarNoQuotes, starsList);
                System.out.println(starsInRange2);
                break;
            case 4:
                int intNeighbors = Integer.parseInt(args.get(0));
                double dPosX = Double.parseDouble(args.get(1));
                double dPosY = Double.parseDouble(args.get(2));
                double dPosZ = Double.parseDouble(args.get(3));
                ArrayList<Star> starsInRange4 = performNaiveNeighbors(intNeighbors, dPosX, dPosY, dPosZ, starsList);
                System.out.println(starsInRange4);
                break;
        }
    }

    public ArrayList<Star> performNaiveNeighbors(int count, String name, ArrayList<Star> alos) {
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

            return performNaiveNeighbors(count, selectedX, selectedY, selectedZ, template);
        }
    }

    public ArrayList<Star> performNaiveNeighbors(int count, double x, double y, double z, ArrayList<Star> alos) {
        if (count == 0) {
            return new ArrayList<>();
        }


        // Ask TA for clone vs copyWithType
        ArrayList<Star> template = copyWithType(alos);
        Collections.sort(template, (star1, star2)
                -> Double.compare(star1.distanceTo(x, y, z), star2.distanceTo(x, y, z)));

        if (count >= template.size()) {
            return template;
        }

        ArrayList<Star> truncatedStarList = new ArrayList<Star>();
        ArrayList<Star> sameValueList = new ArrayList<Star>();

        double distAtCount = template.get(count - 1).distanceTo(x, y, z);
        int whenDistStart = 0;

        for (int i = 0; i < count; i++) {

            if (template.get(i).distanceTo(x, y, z) == distAtCount) {
                whenDistStart = i;
                break;
            }
            truncatedStarList.add(template.get(i));
        }

        for (int j = whenDistStart; j < template.size(); j++) {
            if (template.get(j).distanceTo(x, y, z) != distAtCount) {
                break;
            }
            sameValueList.add(template.get(j));
        }

        // randomly pick (count - whenDistStart + 1) out of the arraylist
        Random ran = new Random();
//        System.out.println(whenDistStart);
//        System.out.println(distAtCount);
//        System.out.println(truncatedStarList);
//        System.out.println(sameValueList);
        for (int k = 0; k < (count - whenDistStart); k++) {
            int selected = ran.nextInt(sameValueList.size());
            truncatedStarList.add(sameValueList.remove(selected));
        }

        return truncatedStarList;
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
        if (starsList.size() == 0) {
            result = false;
            System.out.println("ERROR: No file has been loaded yet");
        }

        // Testing for Valid Args Size
        if (!Arrays.asList(acceptArgs).contains(argSize)) {
            System.out.println("ERROR: Incorrect Number of Arguments for \"naive_radius\"");
            return false;
        }

        // Testing for Valid Radius - Must be Numeric and >= 0
        String sNeighbors = args.get(0);
        if (validator.isArgInteger(sNeighbors)) {
            int intNeighbors = Integer.parseInt(sNeighbors);
            if (intNeighbors < 0) {
                result = false;
                System.out.println("ERROR: Number of Neighbors Cannot Be Negative");
            }
        } else {
            result = false;
            System.out.println("ERROR: Number of Neighbors is Not an Integer");
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
