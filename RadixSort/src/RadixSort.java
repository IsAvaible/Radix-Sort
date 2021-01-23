import java.util.Arrays;

/**
 * This Class is used to sort Arrays with LSD Radix Sort and Base 10. <br>
 * It was written out-of-place and is stable.<br>
 * The method "sort" was overloaded to accept Integer Arrays, Char Arrays and Strings. <br>
 * With the second parameter maxLen the maximum length of digits per int must be specified when sorting an Integer Array.<br>
 *
 * <ul>
 *     <li>Parameters:</li>
 * <ol>
 *     <li>array (int[] / char[] / String)</li>
 *     <li>maxLen (bool) : only when type(array) == int[]</li>
 *     <li>executionTime (bool) : prints the execution Time</li>
 * </ol>
 * <li> Example: RadixSort.sort(new int[]{32, 12, 123}, 3)</li>
 * <li> Author: Simon Conrad</li>
 * </ul>
 */
public class RadixSort {

    public static int[] sort(int[] array, int maxLen){ // Radix Sort (LSD), base 10, Integer Array

        int[][] reformattedArray = toDigitArray(array, maxLen);  // Method to transform each Integer into digit arrays -> 283 -> [2, 8, 3]

        int[] counter = new int[10];                             // Counting array for each digit in the base (10) -> [0, 0, 0, ...]
        int[][] output = new int[array.length][];                // Container for the processed array

        for (int current = maxLen - 1; current >= 0; current--) { // Loop for each digit starting from the last (lsd)

            // (Counting Sort)

            Arrays.fill(counter, 0);                        // Fill the counting array with zeros (reset)

            for (int[] integer : reformattedArray){             // Count over the array
                counter[integer[current]] += 1;
            }

            for (int c = 1; c<counter.length; c++) {           // Create the Radix sums -> l[c] += l[c-1]
                counter[c] += counter[c - 1];
            }

            for (int i = array.length - 1; i >= 0; i--){ // Iterate over each number starting from the last
                output[counter[reformattedArray[i][current]] - 1] = reformattedArray[i]; // Put the digit array at the in the counting array described place of the digit
                counter[reformattedArray[i][current]] -= 1; // Decrease the used counter by 1
            }

            reformattedArray = output.clone(); // Overwrite the old array with the output

        }

        toIntegerArray(array, reformattedArray, maxLen); // Reconverting each digit-array into integers

        return array; //And return the sorted array

    }

    public static int[][] toDigitArray(int[] array, int maxLen) {

        int[][] reformattedArray = new int[array.length][];  //Storage for the digit-arrays -> [[null], [null], ...]

        for (int index = 0; index < array.length; index++) { //Loop to transform each Integer into digit arrays -> 283 -> [2, 8, 3]

            int integer = array[index];                      //Currently processed integer -> 283

            // Determine the length of the currently processed Integer

            int integerLength = integer != 0 ? 0 : 1;        //Ternary operator to prevent errors with integers that are 0 (Set the length to 1 instead of 0)
            long lenCheck = 1;
            while (lenCheck <= integer) {                        //This is used to determine the length of the integer
                integerLength++;
                lenCheck = (lenCheck << 3) + (lenCheck << 1);   //Same as lenCheck *= 10, but faster
            }

            //int integerLength = integer != 0 ? (int) (Math.log10(integer) + 1) : 1; //Other way to determine the length of the Integer, but slower

            // Convert the array into digit-arrays, by using the determined length

            int[] integerDigits = new int[maxLen];           //Create the container for the digits with len d (integerLength) -> [0, 0, 0]

            for (int i = 1; i < integerLength + 1; i++) {    //Loop to add each digit to the array -> [0, 0, 0] -> [2, 8, 3]
                integerDigits[maxLen - i] = (integer % 10);  //Get the last symbol -> (283 % 10 (base)) = 3
                integer /= 10;                               //Divide the integer by 10 to get access to the next symbol -> 283 / 10 = 28
            }

            //Add digit-array to container
            reformattedArray[index] = integerDigits;

        }

        /*
        for (int[] arr : reformattedArray) {
            System.out.println(Arrays.toString(arr));
        }
         */

        return reformattedArray;

    }

    public static int[] toIntegerArray(int[] array, int[][] reformattedArray, int maxLen) {
        for (int index = 0; index < array.length; index++) { //Reconverting each digit-array into integers

            for (int i = 0; i < reformattedArray[index].length; i++) { //Multiply each digit by the power of 10 ^ i following it's position in the array -> [2, 8, 3] -> [2*10^2, 8*10^1, 3*10^0] -> [200, 80, 3]
                reformattedArray[index][i] *= Math.pow(10, maxLen - i - 1);
            }

            int sum = 0;
            for (int integer : reformattedArray[index]) {
                sum += integer;                                      //Add each of the (processed) digits to the sum -> sum = 200 + 80 + 3 = 283
            }

            array[index] = sum;                                      //Overwrite the value in the array with the sum
        }
        return array;
    }


    public static char[] sort(char[] array){ //Method overload to accept char arrays
        int[] convertedArray = new int[array.length];
        for (int i = 0; i < array.length; i++){ // Converting into int over the ASCII standard
            convertedArray[i] = (int) array[i];
        }
        // The ASCII standard is 8 bit, which means it can store up to 256 characters.
        // With this info taken into consideration, the max length of a converted char
        // can be 3.
        sort(convertedArray, 3); // Sorting
        for (int i = 0; i < array.length; i++){ // Reconverting into chars
            array[i] = (char) convertedArray[i];
        }
        return array;
    }

    public static String sort(String string) { //Method overload to accept Strings
        return String.valueOf(sort(string.toCharArray()));
    }

    // Method overload to retrieve the execution time
    public static int[] sort (int[] array, int maxLen, boolean executionTime ) {
        if (!executionTime){ return sort(array, maxLen); }
        long startTime = System.nanoTime();
        sort(array, maxLen);
        long endTime = System.nanoTime();
        System.out.println("Program executed in " + (endTime - startTime) + " nanoseconds.");
        return array;
    }

}
