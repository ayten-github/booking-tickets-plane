package az.edu.turing.domain.entity;

import java.io.Serializable;
import java.util.Objects;

public class PassengerEntity implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public PassengerEntity(String firstName, String lastName, String login, String password) {
        this(null, firstName, lastName, login, password);
    }

    public PassengerEntity(Long id, String firstName, String lastName, String login, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public PassengerEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fullName) {
        this.firstName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String surname) {
        this.lastName = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerEntity that = (PassengerEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(login, that.login) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }

    @Override
    public String toString() {
        return String.format("PassengerEntity{id='%s', firstName='%s', lastName='%s', login='%s'}",
                id, firstName, lastName, login);
    }
}
