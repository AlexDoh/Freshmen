package Freshmen;

import java.util.*;

public class Example {

    private static Map<String, Student> students = new HashMap<>();
    private static Map<String, Professor> professors = new HashMap<>();
    private static Map<String, Group> groups = new HashMap<>();
    private static Map<String, Lecture> lectures = new HashMap<>();

    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        addExampleData();

        System.out.println("Enter your group name:");


        String gr = scanner.nextLine().toLowerCase();
        Group g = groups.get(gr.toLowerCase());

        if (g == null) {
            System.out.println("There is no such group, please run program again and enter correct group");
            System.exit(0);
        }
        System.out.println("Your group is " + g);

        System.out.println("Who are you, student ot professor?");
        System.out.println("Enter \"1\" for Student");
        System.out.println("Enter \"2\" for Professor");
        int occupation = scanner.nextInt();
        scanner.nextLine();

        switch (occupation) {
            case 1:
                g.defineHeadOfGroup();
                System.out.print("The head of group is ");
                System.out.print(g.getHeadOfTheGroup());
                System.out.println(" due to highest amount of activities, popularity and rating");
                break;
            case 2:
                System.out.println("Please enter your name:");
                String pr = scanner.nextLine().toLowerCase();
                Professor p = professors.get(pr.toLowerCase());

                if (p == null) {
                    System.out.println("There is no such user, please run program again and enter correct name");
                    System.exit(0);
                }

                System.out.println("Please enter your password:");
                p.checkPassword();
                System.out.print("Welcome, ");
                System.out.println(p);

                System.out.print("Please enter a name of the lecture for group ");
                System.out.print(g);
                System.out.println(" in such format (lecture:group) :");
                String le = scanner.nextLine().toLowerCase();
                Lecture l = lectures.get(le.toLowerCase());

                if (l == null) {
                    System.out.println("There is no such title of a lecture in student program, please run program again and enter correct title");
                    System.exit(0);
                }

                System.out.println("Please enter presented students:");
                l.runSurveyOfPresentedStudents();
                System.out.print("Today we have ");
                System.out.print(l.getCountOfPresentedStudents());
                System.out.print(" student(s) at the lecture of subject - ");
                System.out.println(p.getSubject());
                l.printAbsentStudents();
                break;
            default:
                System.out.println("You entered wrong value,please try again, enter 1 for student and 2 for professor");
                break;
        }
    }

    private static void addExampleData() {

        Student s;
        s = new Student("John", "Doe", 44, 50, 23);
        students.put(s.toString(), s);
        s = new Student("Jane", "Doe", 87, 12, 70);
        students.put(s.toString(), s);
        s = new Student("Jack", "Jameson", 94, 5, 20);
        students.put(s.toString(), s);
        s = new Student("Janet", "Smith", 43, 87, 15);
        students.put(s.toString(), s);
        s = new Student("Jim", "Wilson", 67, 12, 89);
        students.put(s.toString(), s);

        Professor p;

        p = new Professor("Marshal", "Texas", "Programming", "s0aP");
        professors.put(p.toString().toLowerCase(), p);

        Group gr1 = new Group("P-501");
        gr1.addStudent(students.get("John Doe"));
        gr1.addStudent(students.get("Jane Doe"));
        gr1.addStudent(students.get("Jack Jameson"));

        Group gr2 = new Group("P-502");
        gr2.addStudent(students.get("Janet Smith"));
        gr2.addStudent(students.get("Jim Wilson"));

        groups.put(gr1.toString().toLowerCase(), gr1);
        groups.put(gr2.toString().toLowerCase(), gr2);

        Lecture l1 = new Lecture("OOP:p-501", "OOP" , groups.get("p-501"));
        lectures.put(l1.toString().toLowerCase(), l1);

        Lecture l2 = new Lecture("OOP:p-502", "OOP" , groups.get("p-502"));
        lectures.put(l2.toString().toLowerCase(), l2);
    }

}