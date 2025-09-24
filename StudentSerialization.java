import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 * The Student class implements Serializable to allow its objects
 * to be converted into a byte stream.
 */
class Student implements Serializable {
    // A version UID is recommended for serialization to ensure
    // that the same version of the class is used during deserialization.
    private static final long serialVersionUID = 1L;

    int studentID;
    String name;
    String grade;

    // transient fields are not serialized.
    // For example, if we had a password field, we would mark it as transient.
    // transient String password; 

    public Student(int studentID, String name, String grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student Details:\n  ID: " + studentID + "\n  Name: " + name + "\n  Grade: " + grade;
    }
}

/**
 * This program demonstrates how to serialize a Student object to a file
 * and then deserialize it back into an object.
 */
public class StudentSerialization {
    public static void main(String[] args) {
        // Create a Student object to be serialized.
        Student student = new Student(101, "John Doe", "A+");
        String filename = "student.ser"; // .ser is a common extension for serialized objects.

        // --- Serialization ---
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            
            // The writeObject method serializes the object and writes it to the file.
            out.writeObject(student);
            System.out.println("Student object has been serialized successfully.");
            System.out.println("Data saved to " + filename);

        } catch (IOException e) {
            System.err.println("An error occurred during serialization.");
            e.printStackTrace();
        }

        // --- Deserialization ---
        Student deserializedStudent = null;
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            // The readObject method deserializes the data and reconstructs the object.
            // A cast is needed because readObject returns a generic Object.
            deserializedStudent = (Student) in.readObject();
            System.out.println("\nStudent object has been deserialized successfully.");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("An error occurred during deserialization.");
            e.printStackTrace();
        }

        // Print the deserialized student's data to verify.
        if (deserializedStudent != null) {
            System.out.println("\n--- Verifying Deserialized Data ---");
            System.out.println(deserializedStudent);
        }
    }
}