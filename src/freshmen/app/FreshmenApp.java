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
            String path = reader.readLine();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(path);
            NodeList studentNodes = document.getElementsByTagName("student");

            for (int i = 0; i < studentNodes.getLength(); i++) {
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
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("XML Parser Configuration is corrupted!");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void addExampleProfessors() {
        Professor professor;
        try {
            System.out.println("Enter path to .xml file with professors (example of data is located at " +
                    "src/freshmen/data/professors.xml)");
            String path = reader.readLine();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(path);
            NodeList professorNodes = document.getElementsByTagName("professor");

            for (int i = 0; i < professorNodes.getLength(); i++) {
                Node node = professorNodes.item(i);
                professor = new Professor(node.getChildNodes().item(1).getTextContent(), node.getChildNodes()
                        .item(3).getTextContent(), node.getChildNodes().item(5)
                        .getTextContent(), node.getChildNodes().item(7).getTextContent());
                professors.put(professor.toString().toLowerCase(), professor);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File professors.xml is missing! Please put the file to src/freshmen/data/ and restart" +
                    " the program.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("File professors.xml is corrupted! Please put to the file correct values pf professors.");
            System.exit(0);
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("XML Parser Configuration is corrupted!");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void addExampleGroups() {
        try {
            System.out.println("Enter path to .xml file with groups (example of data is located at " +
                    "src/freshmen/data/groups.xml)");
            String pathToGroup = reader.readLine();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document documentOfGroups = documentBuilder.parse(pathToGroup);
            NodeList groupNodes = documentOfGroups.getElementsByTagName("group");

            for (int i = 0; i < groupNodes.getLength(); i++) {
                Group group = new Group(groupNodes.item(i).getTextContent());

                System.out.print("Enter path to .xml file with students for group - ");
                System.out.print(group.toString());
                System.out.print(" (example of data is located at src/freshmen/data/students_for_group");
                System.out.print(i + 1);
                System.out.println(".xml)");
                String pathToStudents = reader.readLine();
                DocumentBuilderFactory documentBuilderFactory1 = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder1 = documentBuilderFactory1.newDocumentBuilder();
                Document documentOfStudents = documentBuilder1.parse(pathToStudents);
                NodeList studentNodes = documentOfStudents.getElementsByTagName("student");

                for (int j = 0; j < studentNodes.getLength(); j++) {
                    Node node = studentNodes.item(j);
                    group.addStudent(students.get(node.getChildNodes().item(1).getTextContent()));
                }
                groups.put(group.toString().toLowerCase(), group);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Files groups.xml and/or students_for_group<number_of_group>.xml are missing! " +
                    "Please put file(s) to src/freshmen/data/ and restart the program.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Files groups.xml and/or students_for_group<number_of_group>.xml are corrupted! " +
                    "Please put to the file(s) correct values of students and/or students.");
            System.exit(0);
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("XML Parser Configuration is corrupted!");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void addExampleLectures() {
        try {
            System.out.println("Enter path to .xml file with lectures (example of data is located at " +
                    "src/freshmen/data/lectures.xml)");
            String pathToGroup = reader.readLine();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document documentOfLectures = documentBuilder.parse(pathToGroup);
            NodeList lectureNodes = documentOfLectures.getElementsByTagName("lecture");

            for (int i = 0; i < lectureNodes.getLength(); i++) {
                Node lectureNode = lectureNodes.item(i);

                Lecture lecture = new Lecture(lectureNode.getChildNodes().item(1).getTextContent() + ":"
                        + lectureNode.getChildNodes().item(3).getTextContent(), lectureNode.getChildNodes()
                        .item(1).getTextContent(), groups.get(lectureNode.getChildNodes()
                        .item(3).getTextContent().toLowerCase()));
                lectures.put(lecture.toString().toLowerCase(), lecture);
            }
        } catch (NullPointerException e) {
            System.out.println("File lectures.xml is empty! Please put to the file correct values of lectures.");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("File lectures.xml is missing! Please put file to src/freshmen/data/ and restart" +
                    " the program.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("File lectures.txt is corrupted! Please put to the file correct values of lectures.");
            System.exit(0);
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("XML Parser Configuration is corrupted!");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void setGroup() {
        System.out.println("Enter your group name:");

        String groupName = null;
        try {
            groupName = reader.readLine().toLowerCase();
        } catch (IOException e) {
            System.out.println("Input was failed. Please restart the program again.");
            System.exit(0);
        }
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
            try {
                FreshmenApp.occupation = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                System.out.println("Input was failed. Please restart the program again.");
                System.exit(0);
            }
            if (FreshmenApp.occupation != 1 && FreshmenApp.occupation != 2) {
                System.out.println("You entered wrong value, please try again," +
                        " enter 1 for student and 2 for professor");
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
        String professorName = null;
        try {
            professorName = reader.readLine().toLowerCase();
        } catch (IOException e) {
            System.out.println("Input was failed. Please restart the program again.");
            System.exit(0);
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
        } catch (IOException e) {
            System.out.println("Input was failed. Please restart the program again.");
            System.exit(0);
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

}
