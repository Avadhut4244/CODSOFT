import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Student {
    private String name;
    private Map<String, Integer> subjects;

    public Student(String name) {
        this.name = name;
        this.subjects = new HashMap<>();
    }

    public void addSubject(String subjectName, int marks) {
        subjects.put(subjectName, marks);
    }

    public int calculateTotalMarks() {
        int total = 0;
        for (int marks : subjects.values()) {
            total += marks;
        }
        return total;
    }

    public double calculateAveragePercentage() {
        if (subjects.isEmpty()) {
            return 0;
        }
        return ((double) calculateTotalMarks() / subjects.size());
    }

    public String calculateGrade() {
        double averagePercentage = calculateAveragePercentage();
        if (averagePercentage >= 90) {
            return "A";
        } else if (averagePercentage >= 80) {
            return "B";
        } else if (averagePercentage >= 70) {
            return "C";
        } else if (averagePercentage >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    public void displayResults() {
        System.out.println("\nResults for " + name + ":");
        System.out.println("------------------------");
        for (Map.Entry<String, Integer> entry : subjects.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Total Marks: " + calculateTotalMarks());
        System.out.printf("Average Percentage: %.2f%%\n", calculateAveragePercentage());
        System.out.println("Grade: " + calculateGrade());
    }
}

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nStudent Grade Calculator");
            System.out.println("------------------------");
            System.out.println("1. Calculate Grades for a Student");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            
            while (!scanner.hasNextInt()) { 
                System.out.println("Invalid input");
                scanner.next(); 
            }
            int option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    System.out.print("Enter the student's name: ");
                    String studentName = scanner.nextLine().trim();
                    while (studentName.isEmpty()) {
                        System.out.print("Name cannot be empty.");
                        studentName = scanner.nextLine().trim();
                    }

                    Student student = new Student(studentName);

                    System.out.print("Enter the number of subjects: ");
                    while (!scanner.hasNextInt()) { 
                        System.out.println("Invalid input.");
                        scanner.next(); 
                    }
                    int numSubjects = scanner.nextInt();
                    scanner.nextLine(); 

                    while (numSubjects <= 0) {
                        System.out.print("Number of subjects must be greater than zero");
                        numSubjects = scanner.nextInt();
                        scanner.nextLine(); 
                    }

                    for (int i = 0; i < numSubjects; i++) {
                        System.out.print("Enter the name of subject " + (i + 1) + ": ");
                        String subjectName = scanner.nextLine().trim();
                        while (subjectName.isEmpty()) {
                            System.out.print("Subject name cannot be empty. Please enter again: ");
                            subjectName = scanner.nextLine().trim();
                        }

                        System.out.print("Enter marks for " + subjectName + " (out of 100): ");
                        while (!scanner.hasNextInt()) { 
                            System.out.println("Invalid input. Please enter a valid integer.");
                            scanner.next(); 
                        }
                        int marks = scanner.nextInt();
                        scanner.nextLine(); 

                        while (marks < 0 || marks > 100) { 
                            System.out.print("Please enter again: ");
                            marks = scanner.nextInt();
                            scanner.nextLine(); 
                        }
                        
                        student.addSubject(subjectName, marks);
                    }

                    
                    student.displayResults();
                    break;
                case 2:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
