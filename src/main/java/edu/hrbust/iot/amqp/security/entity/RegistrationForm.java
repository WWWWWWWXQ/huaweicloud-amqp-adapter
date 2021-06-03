package edu.hrbust.iot.amqp.security.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Builder
public class RegistrationForm {
    private String username;
    private String password;

    public User toUser(PasswordEncoder encoder){
        return new User(null, username, encoder.encode(password));
    }
}
