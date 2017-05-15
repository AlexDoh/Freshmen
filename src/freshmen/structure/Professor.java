package freshmen.structure;

import java.io.IOException;

import static freshmen.app.FreshmenApp.reader;

public class Professor extends Human {

    private String password;
    private String subject;

    public Professor(String firstName, String lastName, String subject, String password) {
        super(firstName, lastName);
        this.subject = subject;
        this.password = password;
    }

    public String getSubject() {
        return subject;
    }

    public String checkPassword() {
        try {
            String pass = reader.readLine();
            if (pass.equals(password)) {
                return password;
            } else {
                System.out.println("The password is incorrect!");
                System.exit(0);
            }
            return password;
        } catch (IOException e) {
            System.out.println("Input was failed. Please restart the program again.");
            System.exit(0);
            return null;
        }

    }
}
