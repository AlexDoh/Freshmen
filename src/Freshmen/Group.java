package Freshmen;

import java.util.HashSet;
import java.util.Set;

public class Group {

    private String name;
    private Student headOfTheGroup;
    private Set<Student> students = new HashSet<>();

    public Group(String name) {
        this.name = name;
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public void removeStudent(Student s) {
        students.remove(s);
    }

    public void defineHeadOfGroup() {
        int max = 0;
        Student maxS = null;
        for (Student s : students) {
            int rating = s.getCommonRating();
            if (max < rating) {
                max = rating;
                maxS = s;
            }
        }
        headOfTheGroup = maxS;
    }

    public Student getHeadOfTheGroup() {
        return headOfTheGroup;
    }

    public Set<Student> getStudents() {
        return students;

    }

    @Override
    public String toString() {
        return name;
    }

}
