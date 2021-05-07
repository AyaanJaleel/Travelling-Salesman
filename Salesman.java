import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Salesman {
    // Mutation function made to create child paths
    static List<List<Double>> shuffleArray(Double[][] array) {
        //Swapping the order of the 2d array using Math.random
        for (var i = array.length - 1; i > 0; i--) {
            var j = Math.floor(Math.random() * (i + 1));
            var temp = array[i];
            array[i] = array[(int) j];
            array[(int) j] = temp;
        }
        // converting 2d array to 2d ArrayList
        List<List<Double>> cityList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            cityList.add(Arrays.asList(array[i]));
        }
        //Making sure to return to the initial city
        cityList.add(cityList.get(0));
        //Return the child path
        return cityList;
    }
    //A function that calculates the distance between 2 cities according to their x and y coordinates
    private static double calculateDistance(double onex, double twox, double oney, double twoy) {
        double distanceX = Math.abs((twox - onex) * (twox - onex));
        double distanceY = Math.abs((twoy - oney) * (twoy - oney));
        return Math.sqrt(distanceX + distanceY);
    }

    public static void main(String[] args) throws IOException {
        // to record algo in
        long startTime = System.nanoTime();
        // read file into list
        List<String> linesInFile = Files.readAllLines(Paths.get("/Users/ayaanjaleel/Downloads/AI-CW/test4-20.txt"));
        // create 2d array based on number of lines
        List<List<Double>> cityList = new ArrayList<>(linesInFile.size());

        // iterate through every line of the file and split the elements using comma
        linesInFile.forEach(line -> {
            //Adding the city and its x and y coordinates to array
            String[] elements = line.split(",");
            Double[] doubleElements = new Double[elements.length];
            //Converting string values to double
            for (int i = 0; i < elements.length; i++) {
                doubleElements[i] = Double.parseDouble(elements[i].trim());
            }
            //Adding city, and its details to 2d arraylist
            cityList.add(Arrays.asList(doubleElements));
        });
        System.out.println("Total no. of cities: " + cityList.size());
        // Adding city and its details to a double 2d Array.
        Double[][] manyPaths = new Double[cityList.size()][];
        // This is done to create multiple children
        for (int i = 0; i < cityList.size(); i++) {
            manyPaths[i] = cityList.get(i).toArray(new Double[0]);
        }
        // Tours is an arraylist that contains objects (child path, distance)
        ArrayList<Tours> tours = new ArrayList<Tours>();
        //Instructing java to run loop 1000000 times.
        for (int i = 0; i < 50000; i++) {
            //Creating 400 child paths
            var childPath = shuffleArray(manyPaths);
            double total = 0;
            //we are starting from last city, and moving until we reach the last city
            for (int z = cityList.size(); z > 0; z--) {
                //Calculating euclidean distance = x value of city - x value of city before it, y value of city - y value of city before it
                total += calculateDistance(childPath.get(z).get(1), childPath.get(z - 1).get(1),
                        childPath.get(z).get(2), childPath.get(z - 1).get(2));
            }
            // Adding each child path, and its distance to the object arraylist
            Tours a = new Tours(childPath, total);
            tours.add(a);
        }
        //Sorting the child paths in ascending order based on their distance
        Collections.sort(tours);
        System.out.println("=========");

        System.out.println("Best Path:");
        //Printing out the final path
        for(int i=0; i < cityList.size();i++){
            System.out.print(tours.get(0).getPath().get(0).get(i).get(0)+ " -> ");
        }
        System.out.println(tours.get(0).getPath().get(0).get(0).get(0));
        //Choosing the first object that contains the shortest distance (after ascending order)
        System.out.println("=========");
        System.out.println("Shortest Distance: " + tours.get(0).getDistance());
        //Calculating time taken to run algorithm in milliseconds
        System.out.println("=========");
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime - startTime) + " nanoseconds");

    }
}
