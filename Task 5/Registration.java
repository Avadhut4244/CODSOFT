import java.util.*;
import java.util.stream.Collectors;

public class Registration {
    private static final Map<String, Course> courses = new HashMap<>();
    private static final Map<String, Student> students = new HashMap<>();

    public static void main(String[] args) {
        initializeCourses();
        initializeStudents();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Student Course Registration System ===");
            System.out.println("1. Display Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. View Student Courses");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1 -> displayCourses();
                case 2 -> registerCourse(scanner);
                case 3 -> dropCourse(scanner);
                case 4 -> viewStudentCourses(scanner);
                case 5 -> {
                    System.out.println("Thank you for using the system!");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void initializeCourses() {
        courses.put("CS101", new Course("CS101", "Introduction to Computer Science", "Basics of programming", 30, "Monday, Wednesday"));
        courses.put("MATH102", new Course("MATH102", "Calculus", "Mathematics fundamentals", 25, "Tuesday, Thursday"));
        courses.put("ENGL103", new Course("ENGL103", "English Literature", "Literary analysis", 20, "Friday"));
        courses.put("PHYS104", new Course("PHYS104", "Physics Fundamentals", "Basic physics principles", 25, "Monday, Friday"));
       
    }

    private static void initializeStudents() {
        students.put("S001", new Student("S001", "Amar"));
        students.put("S002", new Student("S002", "Rahul"));
        students.put("S003", new Student("S003", "Amol"));
    }

    private static void displayCourses() {
        System.out.println("\n=== Available Courses ===");
        courses.values().forEach(course -> {
            System.out.printf("\nCourse Code: %s\nTitle: %s\nDescription: %s\nCapacity: %d/%d\nSchedule: %s\n",
                    course.getCode(), course.getTitle(), course.getDescription(), course.getRegistered(), course.getCapacity(), course.getSchedule());
        });
    }

    private static void registerCourse(Scanner scanner) {
        System.out.print("\nEnter your student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter course code to register: ");
        String courseCode = scanner.nextLine();

        if (students.containsKey(studentId)) {
            Student student = students.get(studentId);
            Course course = courses.get(courseCode);

            if (course != null && course.getRegistered() < course.getCapacity()) {
                course.register();
                student.addCourse(courseCode);
                System.out.println("Registration successful!");
            } else {
                System.out.println("Course is full");
            }
        } else {
            System.out.println("Student not found!");
        }
    }

    private static void dropCourse(Scanner scanner) {
        System.out.print("\nEnter your student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter course code to drop: ");
        String courseCode = scanner.nextLine();

        if (students.containsKey(studentId)) {
            Student student = students.get(studentId);
            Course course = courses.get(courseCode);

            if (course != null && student.getCourses().contains(courseCode)) {
                course.drop();
                student.removeCourse(courseCode);
                System.out.println("Course dropped successfully!");
            } else {
                System.out.println("You are not registered for this course!");
            }
        } else {
            System.out.println("Student not found!");
        }
    }

    private static void viewStudentCourses(Scanner scanner) {
        System.out.print("\nEnter your student ID: ");
        String studentId = scanner.nextLine();

        if (students.containsKey(studentId)) {
            Student student = students.get(studentId);
            System.out.println("\n=== Student Information ===");
            System.out.printf("Student ID: %s\nName: %s\n", student.getId(), student.getName());
            
            if (!student.getCourses().isEmpty()) {
                System.out.println("\n=== Registered Courses ===");
                student.getCourses().forEach(courseCode -> {
                    Course course = courses.get(courseCode);
                    System.out.printf("\nCourse Code: %s\nTitle: %s\n", course.getCode(), course.getTitle());
                });
            } else {
                System.out.println("\nNo courses registered.");
            }
        } else {
            System.out.println("Student not found!");
        }
    }

    static class Course {
        private final String code;
        private final String title;
        private final String description;
        private final int capacity;
        private int registered;
        private final String schedule;

        public Course(String code, String title, String description, int capacity, String schedule) {
            this.code = code;
            this.title = title;
            this.description = description;
            this.capacity = capacity;
            this.schedule = schedule;
        }

        public String getCode() { return code; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public int getCapacity() { return capacity; }
        public int getRegistered() { return registered; }
        public String getSchedule() { return schedule; }

        public void register() {
            if (registered < capacity) {
                registered++;
            }
        }

        public void drop() {
            if (registered > 0) {
                registered--;
            }
        }
    }

    static class Student {
        private final String id;
        private final String name;
        private final Set<String> courses;

        public Student(String id, String name) {
            this.id = id;
            this.name = name;
            this.courses = new HashSet<>();
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public Set<String> getCourses() { return courses; }

        public void addCourse(String courseCode) {
            courses.add(courseCode);
        }

        public void removeCourse(String courseCode) {
            courses.remove(courseCode);
        }
    }
}
