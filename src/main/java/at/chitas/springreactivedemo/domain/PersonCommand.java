package at.chitas.springreactivedemo.domain;

/**
 * Created by: emil
 * Date: 11/6/21
 */
public class PersonCommand {
    private String firstName;
    private String lastName;

    public PersonCommand(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String sayName() {
        return firstName + " " + lastName;
    }
}
