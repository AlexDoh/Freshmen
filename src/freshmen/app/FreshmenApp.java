package freshmen.app;

import freshmen.structure.Group;
import freshmen.structure.Lecture;
import freshmen.structure.Professor;
import freshmen.structure.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FreshmenApp {

    private static Map<String, Student> students = new HashMap<>();
    private static Map<String, Professor> professors = new HashMap<>();
    private static Map<String, Group> groups = new HashMap<>();
    private static Map<String, Lecture> lectures = new HashMap<>();

    public static final Scanner scanner = new Scanner(System.in);

    private static int occupation;
    private static int groupsCount = 1;
    private static int lecturesCount = 1;
    private static Group group;

    public static void main(String[] args) {
        addExampleData();
        setGroup();
        setOccupation();

        if (FreshmenApp.occupation == 1) {
            loginAsStudent();
        }
        if (FreshmenApp.occupation == 2) {
            loginAsProfessor();
        }

    }

    private static void addExampleData() {
        addExampleStudents();
        addExampleProfessors();
        addExampleGroup();
        addExampleGroup();
        addExampleLecture();
        addExampleLecture();
    }

    private static void addExampleStudents() {
        Student student;
        try {
            System.out.println("Enter path to .xml file with students (example of data is located at " +
                    "src/freshmen/data/students.xml)");
            BufferedReader readerStudents = new BufferedReader(new InputStreamReader(System.in));
            String path = readerStudents.readLine();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(path);
            NodeList studentNodes = document.getElementsByTagName("student");

            for(int i = 0;i < studentNodes.getLength();i++){
                Node node = studentNodes.item(i);
                student = new Student(node.getChildNodes().item(1).getTextContent(), node.getChildNodes()
                        .item(3).getTextContent(), Integer.parseInt(node.getChildNodes().item(5)
                        .getTextContent()),
                        Integer.parseInt(node.getChildNodes().item(7).getTextContent()),
                        Integer.parseInt(node.getChildNodes().item(9).getTextContent()));
                students.put(student.toString(), student);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File students.xml is missing! Please put the file to src/freshmen/data/ and restart" +
                    " the program.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("File students.xml is corrupted! Please put to the file correct values of students.");
            System.exit(0);
        } catch (ParserConfigurationException | SAXException e){
            System.out.println("XML Parser Configuration is corrupted!");
            e.printStackTrace();
            System.exit(0);
        }
        for(String s : students.keySet()){
            System.out.println(s);
        }
    }

    private static void addExampleProfessors() {
        Professor professor;
        try {
            System.out.println("Enter path to .xml file with professors (example of data is located at " +
                    "src/freshmen/data/professors.xml)");
            BufferedReader readerProfessors = new BufferedReader(new InputStreamReader(System.in));
            String path = readerProfessors.readLine();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(path);
            NodeList professorNodes = document.getElementsByTagName("professor");

            for(int i = 0;i < professorNodes.getLength();i++){
                Node node = professorNodes.item(i);
                professor = new Professor(node.getChildNodes().item(1).getTextContent(), node.getChildNodes()
                        .item(3).getTextContent(), node.getChildNodes().item(5)
                        .getTextContent(), node.getChildNodes().item(7).getTextContent());
                professors.put(professor.toString().toLowerCase(), professor);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File professors.xml is missing! Please put the file to src/freshmen/data/ and restart the program.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("File professors.xml is corrupted! Please put to the file correct values pf professors.");
            System.exit(0);
        } catch (ParserConfigurationException | SAXException e){
            System.out.println("XML Parser Configuration is corrupted!");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void addExampleGroup() {
        try {
            BufferedReader readerGroup = new BufferedReader(new FileReader("src/freshmen/data/groups.txt"));
            String line = null;
            for (int i = 1; i <= groupsCount; i++) {
                line = readerGroup.readLine();
            }
            Group group = new Group(line);

            BufferedReader readerStudents = new BufferedReader(new FileReader("src/freshmen/data/students_for_group" + groupsCount + ".txt"));
            while ((line = readerStudents.readLine()) != null) {
                group.addStudent(students.get(line));
            }
            groups.put(group.toString().toLowerCase(), group);
            groupsCount++;
        } catch (FileNotFoundException e) {
            System.out.println("Files groups.txt and/or students_for_group" + groupsCount + ".txt are missing! Please put file(s) to src/freshmen/data/ and restart the program.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Files groups.txt and/or students_for_group" + groupsCount + ".txt are corrupted! Please put to the file(s) correct values of students and/or students.");
            System.exit(0);
        }
    }

    private static void addExampleLecture() {
        try {
            BufferedReader readerLecture = new BufferedReader(new FileReader("src/freshmen/data/lectures.txt"));
            String line = null;
            for (int i = 1; i <= lecturesCount; i++) {
                line = readerLecture.readLine();
            }
            String[] parameters = line.split(" ");
            Lecture lecture = new Lecture(parameters[0] + ":" + parameters[1], parameters[0], groups.get(parameters[1]));
            lectures.put(lecture.toString().toLowerCase(), lecture);
            lecturesCount++;
        } catch (NullPointerException e) {
            System.out.println("File lectures.txt is empty! Please put to the file correct values of lectures.");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("File lectures.txt is missing! Please put file to src/freshmen/data/ and restart the program.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("File lectures.txt is corrupted! Please put to the file correct values of lectures.");
            System.exit(0);
        }
    }

    public static void setGroup() {
        System.out.println("Enter your group name:");

        String groupName = scanner.nextLine().toLowerCase();
        FreshmenApp.group = groups.get(groupName.toLowerCase());

        if (FreshmenApp.group == null) {
            System.out.println("There is no such group, please run program again and enter correct group");
            System.exit(0);
        }
        System.out.println("Your group is " + FreshmenApp.group);

    }

    public static void setOccupation() {
        System.out.println("Who are you, student ot professor?");
        System.out.println("Enter \"1\" for Student");
        System.out.println("Enter \"2\" for Professor");
        try {
            FreshmenApp.occupation = scanner.nextInt();
            scanner.nextLine();
            if (FreshmenApp.occupation != 1 && FreshmenApp.occupation != 2) {
                System.out.println("You entered wrong value, please try again, enter 1 for student and 2 for professor");
                System.exit(0);
            }
        } catch (InputMismatchException e) {
            System.out.println("You entered wrong value, please try again, enter 1 for student and 2 for professor");
            System.exit(0);
        }

    }

    public static void loginAsStudent() {
        FreshmenApp.group.defineHeadOfGroup();
        System.out.print("The head of group is ");
        System.out.print(FreshmenApp.group.getHeadOfTheGroup());
        System.out.println(" due to highest amount of activities, popularity and rating");
    }

    public static void loginAsProfessor() {
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
