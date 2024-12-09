package cs302.project.student_web.model.dto;

public class UserLoginDto {
    private String username;
    private String password;
    public UserLoginDto() {
        super();
    }
    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    };

    public String getPassword() {
        return password;
    };


}
