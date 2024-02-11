import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem {
    private List<Student> students;

    public StudentManagementSystem() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(int rollNumber) {
        students.removeIf(student -> student.getRollNumber() == rollNumber);
    }

    public Student searchStudent(int rollNumber) {
        return students.stream()
                .filter(student -> student.getRollNumber() == rollNumber)
                .findFirst()
                .orElse(null);
    }

    public void displayAllStudents() {
        students.forEach(System.out::println);
    }

    public void saveDataToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(students);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDataFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            students = (List<Student>) ois.readObject();
            System.out.println("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem system = new StudentManagementSystem();

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Save Data to File");
            System.out.println("6. Load Data from File");
            System.out.println("7. Exit");

            System.out.print("Enter your choice (1-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student roll number: ");
                    int rollNumber = scanner.nextInt();
                    System.out.print("Enter student grade: ");
                    char grade = scanner.next().charAt(0);
                    Student newStudent = new Student(name, rollNumber, grade);
                    system.addStudent(newStudent);
                    break;

                case 2:
                    System.out.print("Enter roll number to remove: ");
                    int rollToRemove = scanner.nextInt();
                    system.removeStudent(rollToRemove);
                    break;

                case 3:
                    System.out.print("Enter roll number to search: ");
                    int rollToSearch = scanner.nextInt();
                    Student foundStudent = system.searchStudent(rollToSearch);
                    if (foundStudent != null) {
                        System.out.println("Student found: " + foundStudent);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    System.out.println("All Students:");
                    system.displayAllStudents();
                    break;

                case 5:
                    System.out.print("Enter file name to save data: ");
                    String saveFileName = scanner.next();
                    system.saveDataToFile(saveFileName);
                    break;

                case 6:
                    System.out.print("Enter file name to load data: ");
                    String loadFileName = scanner.next();
                    system.loadDataFromFile(loadFileName);
                    break;

                case 7:
                    System.out.println("Exiting. Thank you!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }
}
