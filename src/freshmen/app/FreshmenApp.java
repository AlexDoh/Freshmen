package freshmen.app;

import freshmen.structure.Group;
import freshmen.structure.Lecture;
import freshmen.structure.Professor;
import freshmen.structure.Student;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class FreshmenApp {

    private static Map<String, Student> students = new HashMap<>();
    private static Map<String, Professor> professors = new HashMap<>();
    private static Map<String, Group> groups = new HashMap<>();
    private static Map<String, Lecture> lectures = new HashMap<>();

    public static final Scanner scanner = new Scanner(System.in);

    private static int occupation;
    private static Group group;

    public static void main(String[] args) {
        addExampleData();
        setGroup();
        setOccupation();

        if(FreshmenApp.occupation == 1){
            loginAsStudent();
        }
        if(FreshmenApp.occupation == 2){
            loginAsProfessor();
        }

    }

    private static void addExampleData() {
        addExampleStudents();
        addExampleProfessors();
        addExampleGroups();
        addExampleLectures();
    }

    private static void addExampleStudents(){
        Student student;
        student = new Student("John", "Doe", 44, 50, 23);
        students.put(student.toString(), student);
        student = new Student("Jane", "Doe", 87, 12, 70);
        students.put(student.toString(), student);
        student = new Student("Jack", "Jameson", 94, 5, 20);
        students.put(student.toString(), student);
        student = new Student("Janet", "Smith", 43, 87, 15);
        students.put(student.toString(), student);
        student = new Student("Jim", "Wilson", 67, 12, 89);
        students.put(student.toString(), student);
    }

    private static void addExampleProfessors(){
        Professor professor;

        professor = new Professor("Marshal", "Texas", "Programming", "s0aP");
        professors.put(professor.toString().toLowerCase(), professor);
    }

    private static void addExampleGroups(){
        Group group1 = new Group("P-501");
        group1.addStudent(students.get("John Doe"));
        group1.addStudent(students.get("Jane Doe"));
        group1.addStudent(students.get("Jack Jameson"));

        Group group2 = new Group("P-502");
        group2.addStudent(students.get("Janet Smith"));
        group2.addStudent(students.get("Jim Wilson"));

        groups.put(group1.toString().toLowerCase(), group1);
        groups.put(group2.toString().toLowerCase(), group2);
    }

    private static void addExampleLectures(){
        Lecture lecture1 = new Lecture("OOP:p-501", "OOP", groups.get("p-501"));
        lectures.put(lecture1.toString().toLowerCase(), lecture1);

        Lecture lecture2 = new Lecture("OOP:p-502", "OOP", groups.get("p-502"));
        lectures.put(lecture2.toString().toLowerCase(), lecture2);
    }

    public static void setGroup(){
        System.out.println("Enter your group name:");

        String groupName = scanner.nextLine().toLowerCase();
        FreshmenApp.group = groups.get(groupName.toLowerCase());

        if (FreshmenApp.group == null) {
            System.out.println("There is no such group, please run program again and enter correct group");
            System.exit(0);
        }
        System.out.println("Your group is " + FreshmenApp.group);

    }

    public static void setOccupation(){
        System.out.println("Who are you, student ot professor?");
        System.out.println("Enter \"1\" for Student");
        System.out.println("Enter \"2\" for Professor");
        try{
            FreshmenApp.occupation = scanner.nextInt();
            scanner.nextLine();
            if(FreshmenApp.occupation != 1 && FreshmenApp.occupation != 2){
                System.out.println("You entered wrong value, please try again, enter 1 for student and 2 for professor");
                System.exit(0);
            }
        } catch(InputMismatchException e){
            System.out.println("You entered wrong value, please try again, enter 1 for student and 2 for professor");
            System.exit(0);
        }

    }

    public static void loginAsStudent(){
        FreshmenApp.group.defineHeadOfGroup();
        System.out.print("The head of group is ");
        System.out.print(FreshmenApp.group.getHeadOfTheGroup());
        System.out.println(" due to highest amount of activities, popularity and rating");
    }

    public static void loginAsProfessor(){
        System.out.println("Please enter your full name:");
        String professorName = scanner.nextLine().toLowerCase();
        Professor selectedProfessor = professors.get(professorName.toLowerCase());

        if (selectedProfessor == null) {
            System.out.println("There is no such user, please run program again and enter correct name");
            System.exit(0);
        }

        System.out.println("Please enter your password:");
        selectedProfessor.checkPassword();
        System.out.print("Welcome, ");
        System.out.println(selectedProfessor);

        System.out.print("Please enter a name of the lecture for group ");
        System.out.print(FreshmenApp.group);
        System.out.println(" in such format (lecture:group) :");
        String lectureName = scanner.nextLine().toLowerCase();
        Lecture selectedLecture = lectures.get(lectureName.toLowerCase());

        if (selectedLecture == null) {
            System.out.println("There is no such title of a lecture in student program, please run program again and enter correct title");
            System.exit(0);
        }

        System.out.println("Please enter presented students:");
        selectedLecture.runSurveyOfPresentedStudents();
        System.out.print("Today we have ");
        System.out.print(selectedLecture.getCountOfPresentedStudents());
        System.out.print(" student(s) at the lecture of subject - ");
        System.out.println(selectedProfessor.getSubject());
        selectedLecture.printAbsentStudents();
    }

}
