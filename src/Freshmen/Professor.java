package Freshmen;

import static Freshmen.Example.scanner;

public class Professor extends Human {

    private String password;
    private String subject;

    public Professor(String name, String surname, String subject, String password) {
        super(name, surname);
        this.subject = subject;
        this.password = password;
    }

    public String getSubject() {
        return subject;
    }

    public String checkPassword() {
        String pass = scanner.nextLine();
        if (pass.equals(password)) {
            return password;
        } else {
            System.out.println("The password is incorrect!");
            System.exit(0);
        }return password;
    }
}