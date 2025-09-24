import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class represents an Employee.
 * We will store its data as a formatted string in a text file.
 */
class Employee {
    String name;
    int id;
    String designation;
    double salary;

    public Employee(String name, int id, String designation, double salary) {
        this.name = name;
        this.id = id;
        this.designation = designation;
        this.salary = salary;
    }

    // A formatted string representation for saving to a file.
    public String toFileString() {
        return id + "," + name + "," + designation + "," + salary;
    }

    // A display-friendly string representation.
    @Override
    public String toString() {
        return "Employee ID: " + id + " | Name: " + name + " | Designation: " + designation + " | Salary: " + salary;
    }
}

/**
 * A menu-driven application for managing employee records using file handling.
 * This implementation uses a simple text file with comma-separated values.
 */
public class EmployeeManagementSystem {
    private static final String FILE_NAME = "employees.txt";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addEmployee();
                    break;
                case "2":
                    displayAllEmployees();
                    break;
                case "3":
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    return; // Exit the main method and terminate the program
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
            System.out.println(); // Add a newline for better readability
        }
    }

    private static void displayMenu() {
        System.out.println("--- Employee Management System ---");
        System.out.println("1. Add an Employee");
        System.out.println("2. Display All Employees");
        System.out.println("3. Exit the Application");
        System.out.println("----------------------------------");
    }

    private static void addEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Employee Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Employee Designation: ");
            String designation = scanner.nextLine();

            System.out.print("Enter Employee Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());
            
            Employee emp = new Employee(name, id, designation, salary);

            // Use try-with-resources to ensure the writer is closed automatically.
            // 'true' in FileWriter constructor enables append mode.
            try (FileWriter fw = new FileWriter(FILE_NAME, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                
                bw.write(emp.toFileString());
                bw.newLine(); // Add a new line for the next entry
            }

            System.out.println("Employee added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter correct numeric values for ID and Salary.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    private static void displayAllEmployees() {
        System.out.println("\n--- All Employee Records ---");
        // Use try-with-resources to ensure the reader is closed automatically.
        try (FileReader fr = new FileReader(FILE_NAME);
             BufferedReader br = new BufferedReader(fr)) {
            
            String line;
            boolean foundRecords = false;
            while ((line = br.readLine()) != null) {
                foundRecords = true;
                String[] details = line.split(",");
                if (details.length == 4) {
                    // Create a temporary Employee object to use its toString() method
                    Employee emp = new Employee(
                        details[1],                          // name
                        Integer.parseInt(details[0]),        // id
                        details[2],                          // designation
                        Double.parseDouble(details[3])       // salary
                    );
                    System.out.println(emp);
                }
            }
            if (!foundRecords) {
                System.out.println("No employee records found. Add an employee first.");
            }
        } catch (IOException e) {
            System.out.println("No employee records found yet or error reading file. Please add an employee first.");
        } catch (NumberFormatException e) {
            System.err.println("Error parsing data from file. The file may be corrupted.");
        }
        System.out.println("----------------------------");
    }
}