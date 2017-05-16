package freshmen.app;

import freshmen.structure.Group;
import freshmen.structure.Lecture;
import freshmen.structure.Professor;
import freshmen.structure.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FreshmenApp {

    private static Map<String, Student> students = new HashMap<>();
    private static Map<String, Professor> professors = new HashMap<>();
    private static Map<String, Group> groups = new HashMap<>();
    private static Map<String, Lecture> lectures = new HashMap<>();

    public static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static int occupation;
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
        addExampleGroups();
        addExampleLectures();
    }

    private static void addExampleStudents() {
        Student student;
        try {
            System.out.println("Enter path to .xml file with students (example of data is located at " +
                    "src/freshmen/data/students.xml)");
            NodeList studentNodes = getDocumentInstance(reader.readLine()).getElementsByTagName("student");

            for (int i = 0; i < studentNodes.getLength(); i++) {
                Element element = (Element) studentNodes.item(i);
                student = new Student(element.getElementsByTagName("firstName").item(0).getTextContent(),
                        element.getElementsByTagName("lastName").item(0).getTextContent(),
                        Integer.parseInt(element.getElementsByTagName("activities").item(0).getTextContent()),
                        Integer.parseInt(element.getElementsByTagName("popularityLevel").item(0)
                                .getTextContent()),
                        Integer.parseInt(element.getElementsByTagName("ratingLevelFromSchool").item(0)
                                .getTextContent()));
                students.put(student.toString(), student);
            }
        } catch (Exception e) {
            handleExceptions(e);
        }
    }

    private static void addExampleProfessors() {
        Professor professor;
        try {
            System.out.println("Enter path to .xml file with professors (example of data is located at " +
                    "src/freshmen/data/professors.xml)");
            NodeList professorNodes = getDocumentInstance(reader.readLine()).getElementsByTagName("professor");

            for (int i = 0; i < professorNodes.getLength(); i++) {
                Element element = (Element) professorNodes.item(i);
                professor = new Professor(element.getElementsByTagName("firstName").item(0).getTextContent(),
                        element.getElementsByTagName("lastName").item(0).getTextContent(),
                        element.getElementsByTagName("subject").item(0).getTextContent(),
                        element.getElementsByTagName("password").item(0).getTextContent());
                professors.put(professor.toString().toLowerCase(), professor);
            }
        } catch (Exception e) {
            handleExceptions(e);
        }
    }

    private static void addExampleGroups() {
        try {
            System.out.println("Enter path to .xml file with groups (example of data is located at " +
                    "src/freshmen/data/groups.xml)");
            NodeList groupNodes = getDocumentInstance(reader.readLine()).getElementsByTagName("group");

            for (int i = 0; i < groupNodes.getLength(); i++) {
                Group group = new Group(groupNodes.item(i).getTextContent());

                System.out.print("Enter path to .xml file with students for group - ");
                System.out.print(group.toString());
                System.out.print(" (example of data is located at src/freshmen/data/students_for_group");
                System.out.print(i + 1);
                System.out.println(".xml)");
                String pathToStudents = reader.readLine();
                NodeList studentNodes = getDocumentInstance(pathToStudents).getElementsByTagName("student");

                for (int j = 0; j < studentNodes.getLength(); j++) {
                    Element element = (Element) studentNodes.item(j);
                    group.addStudent(students.get(element.getElementsByTagName("fullName").item(0)
                            .getTextContent()));
                }
                groups.put(group.toString().toLowerCase(), group);
            }

        } catch (Exception e) {
            handleExceptions(e);
        }
    }

    private static void addExampleLectures() {
        try {
            System.out.println("Enter path to .xml file with lectures (example of data is located at " +
                    "src/freshmen/data/lectures.xml)");
            NodeList lectureNodes = getDocumentInstance(reader.readLine()).getElementsByTagName("lecture");

            for (int i = 0; i < lectureNodes.getLength(); i++) {
                Element element = (Element) lectureNodes.item(i);

                Lecture lecture = new Lecture(element.getElementsByTagName("name").item(0)
                        .getTextContent() + ":" + element.getElementsByTagName("group").item(0)
                        .getTextContent(), element.getElementsByTagName("name").item(0).getTextContent(),
                        groups.get(element.getElementsByTagName("group").item(0).getTextContent()
                                .toLowerCase()));
                lectures.put(lecture.toString().toLowerCase(), lecture);
            }
        } catch (Exception e) {
            handleExceptions(e);
        }
    }

    private static void setGroup() {
        System.out.println("Enter your group name:");

        String groupName = null;
        try {
            groupName = reader.readLine().toLowerCase();
        } catch (Exception e) {
            handleExceptions(e);
        }
        FreshmenApp.group = groups.get(groupName.toLowerCase());

        if (FreshmenApp.group == null) {
            System.out.println("There is no such group, please run program again and enter correct group");
            System.exit(0);
        }
        System.out.println("Your group is " + FreshmenApp.group);

    }

    private static void setOccupation() {
        System.out.println("Who are you, student ot professor?");
        System.out.println("Enter \"1\" for Student");
        System.out.println("Enter \"2\" for Professor");
        try {
            FreshmenApp.occupation = Integer.parseInt(reader.readLine());

            if (FreshmenApp.occupation != 1 && FreshmenApp.occupation != 2) {
                System.out.println("You entered wrong value, please try again," +
                        " enter 1 for student and 2 for professor");
                System.exit(0);
            }
        } catch (Exception e) {
            handleExceptions(e);
        }

    }

    private static void loginAsStudent() {
        FreshmenApp.group.defineHeadOfGroup();
        System.out.print("The head of group is ");
        System.out.print(FreshmenApp.group.getHeadOfTheGroup());
        System.out.println(" due to highest amount of activities, popularity and rating");
    }

    private static void loginAsProfessor() {
        System.out.println("Please enter your full name:");
        String professorName = null;
        try {
            professorName = reader.readLine().toLowerCase();
        } catch (Exception e) {
            handleExceptions(e);
        }
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
        String lectureName = null;
        try {
            lectureName = reader.readLine().toLowerCase();
        } catch (Exception e) {
            handleExceptions(e);
        }
        Lecture selectedLecture = lectures.get(lectureName.toLowerCase());

        if (selectedLecture == null) {
            System.out.println("There is no such title of a lecture in student program," +
                    " please run program again and enter correct title");
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

    private static Document getDocumentInstance(String path){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            return documentBuilder.parse(path);
        } catch (Exception e) {
            handleExceptions(e);
            return null;
        }
    }
    public static void handleExceptions(Exception e){
        if(e instanceof FileNotFoundException){
            System.out.println(e.getClass().getSimpleName());
            System.out.println("File is missing! Write the correct path and restart the program.");
            System.exit(0);
        }
        if(e instanceof IOException){
            System.out.println(e.getClass().getSimpleName());
            System.out.println("There was a problem with file input! Make sure, that all data is set" +
                    " and restart the program again.");
            System.exit(0);
        }
        if(e instanceof NumberFormatException || e instanceof InputMismatchException){
            System.out.println(e.getClass().getSimpleName());
            System.out.println("You have entered a wrong value, please restart the program and try again.");
            System.exit(0);
        }
        if (e instanceof ParserConfigurationException || e instanceof SAXException){
            System.out.println(e.getClass().getSimpleName());
            System.out.println("XML Parser Configuration exception. Make sure, that you entered corrected data.");
            System.exit(0);
        }
    }
}
