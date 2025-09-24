import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class SumOfIntegers {

    public static void main(String[] args) {
        // Create a list to store Integer objects.
        List<Integer> integerList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter integers separated by spaces (e.g., 10 20 30), then press Enter:");
        String inputLine = scanner.nextLine();
        
        // Split the input string into an array of string numbers.
        String[] numberStrings = inputLine.split("\\s+");

        // --- Autoboxing and Parsing ---
        System.out.println("\n--- Processing Inputs ---");
        for (String str : numberStrings) {
            try {
                // 1. Parsing: Convert string to a primitive int.
                int primitiveInt = Integer.parseInt(str);
                
                // 2. Autoboxing: The primitive int is automatically converted to an Integer object
                //    when added to the ArrayList<Integer>.
                integerList.add(primitiveInt); 
                System.out.println("Parsed and autoboxed: " + str);
            } catch (NumberFormatException e) {
                System.out.println("Skipping invalid input: '" + str + "' is not a valid integer.");
            }
        }

        // --- Unboxing ---
        int totalSum = 0;
        // The enhanced for-loop automatically unboxes each Integer object from the list
        // into a primitive int for the addition operation.
        for (Integer integerObject : integerList) {
            totalSum += integerObject; // Unboxing happens here: integerObject is converted to int.
        }

        System.out.println("\n--- Calculation Result ---");
        System.out.println("The numbers you entered are: " + integerList);
        System.out.println("The total sum is: " + totalSum);
        
        scanner.close();
    }
}