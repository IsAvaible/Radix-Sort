import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        // ! Read the javadoc if you don't understand how to use the RadixSort Class !

        // Integer Sorting
        int[] intArray = {234, 244, 22, 12, 49, 1, 0, 442, 999}; // Creating a small array
        RadixSort.sort(intArray, 3, true); // And sorting it
        System.out.println(Arrays.toString(intArray));

        intArray = randArray(100000, 1000); // Creating a big array (100 000 elements)
        RadixSort.sort(intArray, 3, true); // And sorting it
        // No print, because it's a big array

        // It can also sort char arrays
        System.out.println(Arrays.toString(RadixSort.sort(new char[]{'f', 'a', 'e', 'g', 'd', 'b', 'c'})));
        // And even strings
        System.out.println(RadixSort.sort("Hello, World!"));

    }

    // Method to create arrays with given length filled with random numbers with given bound
    public static int[] randArray(int len, int bound) {
        int[] array = new int[len];
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            array[i] = random.nextInt(bound);
        }
        return array;
    }

}
