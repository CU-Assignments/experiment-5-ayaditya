// Question 1: Write a Java program to calculate the sum of a list of integers using autoboxing and unboxing. 
//Include methods to parse strings into their respective wrapper classes (e.g., Integer.parseInt()).

import java.util.ArrayList;
import java.util.List;

public class SumCalculator {

    public static List<Integer> parseStringsToIntegers(List<String> stringList) {
        List<Integer> integerList = new ArrayList<>();
        for (String str : stringList) {
            // Using Integer.parseInt() to convert String to int, then autoboxing to Integer
            integerList.add(Integer.parseInt(str));
        }
        return integerList;
    }

    public static int calculateSum(List<Integer> integerList) {
        int sum = 0;
        for (Integer num : integerList) {
            // Unboxing Integer to int and adding to the sum
            sum += num;
        }
        return sum;
    }

    public static void main(String[] args) {
        List<String> stringList = List.of("10", "20", "30", "40", "50");

        List<Integer> integerList = parseStringsToIntegers(stringList);

        int sum = calculateSum(integerList);
        System.out.println("The sum of the integers is: " + sum);
    }
}

// Question 2: Create a Java program to serialize and deserialize a Student
// object. The program should:
// Serialize a Student object (containing id, name, and GPA) and save it to a
// file.
// Deserialize the object from the file and display the student details.
// Handle FileNotFoundException, IOException, and ClassNotFoundException using
// exception handling.

import java.io.Serializable;
import java.io.*;

public class Student implements Serializable {
    private int id;
    private String name;
    private double gpa;

    // Constructor
    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gpa=" + gpa +
                '}';
    }
}

public class SerializationDemo {
    public static void main(String[] args) {
        // Create a Student object
        Student student = new Student(1, "John Doe", 3.8);

        // Serialize the Student object to a file
        serializeStudent(student, "student.ser");

        // Deserialize the Student object from the file
        Student deserializedStudent = deserializeStudent("student.ser");

        // Display the deserialized Student object
        if (deserializedStudent != null) {
            System.out.println("Deserialized Student: " + deserializedStudent);
        }
    }

    // Method to serialize a Student object to a file
    public static void serializeStudent(Student student, String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(student); // Serialize the object
            System.out.println("Student object serialized and saved to " + fileName);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error during serialization: " + e.getMessage());
        }
    }

    // Method to deserialize a Student object from a file
    public static Student deserializeStudent(String fileName) {
        Student student = null;
        try (FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            student = (Student) in.readObject(); // Deserialize the object
            System.out.println("Student object deserialized from " + fileName);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error during deserialization: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
        }
        return student;
    }
}

// Question 3: Create a menu-based Java application with the following options.
// 1.Add an Employee
// 2. Display All
// 3. Exit If option 1 is selected, the application should gather details of the
// employee like employee name, employee id, designation and salary and store it
// in a file.
// If option 2 is selected, the application should display all the employee
// details. If option 3 is selected the application should exit

import java.io.*;
import java.util.*;

public class EmployeeManager {
    private static final String FILE_NAME = "employees.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Add an Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    displayAllEmployees();
                    break;
                case 3:
                    System.out.println("Exiting the application.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addEmployee(Scanner scanner) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {

            System.out.print("Enter Employee ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Employee Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Designation: ");
            String designation = scanner.nextLine();
            System.out.print("Enter Salary: ");
            double salary = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            out.println(id + "," + name + "," + designation + "," + salary);
            System.out.println("Employee added successfully.");

        } catch (IOException e) {
            System.out.println("An error occurred while adding the employee.");
            e.printStackTrace();
        }
    }

    private static void displayAllEmployees() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("Employee Details:");
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                System.out.println("ID: " + details[0] + ", Name: " + details[1] +
                        ", Designation: " + details[2] + ", Salary: " + details[3]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No employee records found.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the employee records.");
            e.printStackTrace();
        }
    }
}