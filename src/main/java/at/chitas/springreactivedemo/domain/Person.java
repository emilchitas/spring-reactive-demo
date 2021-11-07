package at.chitas.springreactivedemo.domain;

/**
 * Created by: emil
 * Date: 11/6/21
 */
public class Person {
    private String firstName;
    private String lastName;

    /**
     * Create a person object without any properties.
     */
    public Person() {
    }

    /**
     * Create a person object.
     *
     * @param firstName the peson's first name
     * @param lastName  the person's last name
     */
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
