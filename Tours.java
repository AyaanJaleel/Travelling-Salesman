import java.util.Arrays;
import java.util.List;
//making an array of objects to store each child path, and its distance
public class Tours implements Comparable<Tours>{
    private double distance;
    private List<List<Double>> path;
    //Object template {child path, distance)
    public Tours(List<List<Double>> path, double distance) {
        this.path = path;
        this.distance = distance;
    }
    //To find a particular child path
    public List<List<List<Double>>> getPath(){
        return Arrays.asList(path);
    }
    //To find the distance of a particular child path
    public double getDistance(){
        return distance;
    }
    @Override
    //help to sort the arraylist in ascending order
    public int compareTo(Tours o) {
        return (int) (this.distance - o.distance);
    }
}
