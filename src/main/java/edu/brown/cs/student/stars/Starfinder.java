package edu.brown.cs.student.stars;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

// import java.util.Pair;



public class Starfinder {

//    private ArrayList<Star> starsList = new ArrayList<Star>();
//
//    public Starfinder() { }
//
//    public void changeFile(String filename) {
//        starsList.clear();
//
//        try (BufferedReader csvReader = new BufferedReader(new FileReader(new File(filename).toPath()))) {
//            csvReader.readLine();
//            String line;
//            while ((line = csvReader.readLine()) != null) {
//                starsList.add(lineToStar(csvReader.readLine()));
//            }
//        } catch (IOException e) {
//            System.out.println("ERROR: File Name/Path/Content is not valid");
//        }
//    }
//
//    private Star lineToStar(String line) {
//        // TODO: Convert line to JSON to be more scalable
//        String[] starArgsArray = line.split(",");
//
//        return new Star(
//                starArgsArray[0],
//                starArgsArray[1],
//                Double.parseDouble(starArgsArray[2]),
//                Double.parseDouble(starArgsArray[3]),
//                Double.parseDouble(starArgsArray[4]));
//    }
//
//    public Integer getCount() {
//        return starsList.size();
//    }
//
//    private Optional<Star> findStarWithName(String name) {
//        for(Star eachStar : starsList) {
//            if (eachStar.getName().equals(name)) {
//                return Optional.of(eachStar);
//            }
//        }
//        return Optional.empty();
//    }
//
//    private ArrayList<Star> findStarWithCord(double x, double y, double z) {
//        ArrayList<Star> template = new ArrayList<Star>();
//        for(Star eachStar : starsList) {
//            if ((x == eachStar.getX()) && (y == eachStar.getY()) && (z == eachStar.getZ())) {
//                template.add(eachStar);
//            }
//        }
//        return template;
//    }
//
//    public ArrayList<Star> naiveNeighbors(int count, double x, double y, double z) {
//        ArrayList<Star> template = copyWithType(starsList);
//        ArrayList<Pair<Star, Number>> pairTemplate = template.stream()
//                .map(star -> new Pair <Star, Double> (star, star.distanceTo(x, y, z)))
//                .collect(Collectors.toList());
//
//        ArrayList<Pair<Star, Number>> sortedTemplate = Collections.sort(pairTemplate, new Comparator<Pair<Star, Double>>() {
//            public int compare(Pair<Star, Double> starDist1, Pair<Star, Double> starDist2) {
//                return (starDist1.getR() < starDist2.getR()) ? 1 : 0;
//            }
//        });
//
//        // return template;
//        ArrayList<Star> truncatedStarList = new ArrayList<Star>();
//        ArrayList<Star> sameValueList = new ArrayList<Star>();
//
//        double distAtCount = sortedTemplate.get(count).getR();
//        int whenDistStart = 0;
//
//        for (int i = 0; i < count; i++) {
//            if (sortedTemplate.get(i).getR() == distAtCount) {
//                whenDistStart = i;
//                break;
//            }
//            truncatedStarList.add(sortedTemplate.get(i).getL());
//        }
//
//        for (int j = whenDistStart; j < template.size(); j++) {
//            if (sortedTemplate.get(j).getR() != distAtCount) {
//                break;
//            }
//            sameValueList.add(sortedTemplate.get(j).getL());
//        }
//
//        // randomly pick (count - whenDistStart + 1) out of the arraylist
//        Random ran = new Random();
//
//        for (int k = 0; k < (count - whenDistStart + 1); k++) {
//            int selected = ran.nextInt(sameValueList.size());
//            truncatedStarList.add(sameValueList.remove(selected));
//        }
//
//        return truncatedStarList;
//
//    }
//
//
//    public Optional<ArrayList<Star>> naiveRadius(double r, double x, double y, double z) {
//        if (r < 0) {
//            return Optional.empty();
//        }
//
//        if (r == 0) {
//            return Optional.of(findStarWithCord(x, y, z));
//        }
//
//        // Otherwise, r > 0 -> ASK TA for Style
//        ArrayList<Star> template = copyWithType(starsList);
//        template.removeIf(star -> (star.distanceTo(x, y, z) > r));
//
//        return Optional.of(template);
//    }
//
//    public Optional<ArrayList<Star>> naiveRadius(double r, String name) {
//        Optional<Star> selectedStar = findStarWithName(name);
//        double selectedX = selectedStar.getX();
//        double selectedY = selectedStar.getY();
//        double selectedZ = selectedStar.getZ();
//
//        return selectedStar.isEmpty() ? Optional.empty() : naiveRadius(r, selectedX, selectedY, selectedZ);
//    }
//
//    private <A> ArrayList<A> copyWithType(ArrayList<A> aloa) {
//        ArrayList<A> template = new ArrayList<>();
//
//        for (A item : aloa) {
//            template.add(item);
//        }
//
//        return template;
//    }

}
