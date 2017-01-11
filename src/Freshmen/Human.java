package Freshmen;

public class Human {

    private String name;
    private String surname;
    private String fullName;

    public Human(String name, String surname) {
        this.name = name;
        this.surname = surname;
        fullName = name + ' ' + surname;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    public String getFullName() {
        return fullName;
    }

}
