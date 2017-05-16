package freshmen.structure;

import freshmen.app.FreshmenApp;

import static freshmen.app.FreshmenApp.reader;

import java.util.HashSet;
import java.util.Set;


public class Lecture {

    private String name;
    private String id;
    private Group group;
    private int presenceCounter = 0;
    private Set<Student> studentsPresence = new HashSet<>();

    public Lecture(String id, String name, Group group) {
        this.name = name;
        this.group = group;
        this.id = id;
    }

    public void runSurveyOfPresentedStudents() {
        Set<Student> students = group.getStudents();
        for (Student s : students) {
            System.out.print("Does ");
            System.out.print(s.getFullName());
            System.out.println(" present today? (Y/N)");
            try {
                String stud = reader.readLine().toLowerCase();
                switch (stud) {
                    case "y":
                    case "yes":
                        studentsPresence.add(s);
                        presenceCounter++;
                        break;
                    case "n":
                    case "no":
                        break;
                    default:
                        System.out.println("You have entered a wrong value, please launch the program again");
                        System.exit(0);
                }
            } catch (Exception e) {
                FreshmenApp.handleExceptions(e);
            }
        }
    }

    public int getCountOfPresentedStudents() {
        return presenceCounter;
    }

    public void printAbsentStudents() {
        Set<Student> students = group.getStudents();
        for (Student s : students) {
            if (!studentsPresence.contains(s)) {
                System.out.print(s.getFullName());
                System.out.println(" isn't presented today");
            }
        }
    }

    @Override
    public String toString() {
        return id.replaceAll("\\s+", "");
    }
}
