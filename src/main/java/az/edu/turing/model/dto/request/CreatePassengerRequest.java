package az.edu.turing.model.dto.request;

public class CreatePassengerRequest {

    private final String firstName;
    private final String lastName;
    private final String login;

    public CreatePassengerRequest(String firstName, String lastName, String login) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
