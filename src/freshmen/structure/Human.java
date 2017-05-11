package freshmen.structure;

public class Human {

    private String firstName;
    private String lastName;

    public Human(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    public String getFullName() {
        return this.firstName + ' ' + this.lastName;
    }

}
