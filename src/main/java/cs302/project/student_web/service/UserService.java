package cs302.project.student_web.service;

import cs302.project.student_web.exception.UserRegistrationException;
import cs302.project.student_web.model.UserModel;
import cs302.project.student_web.model.dto.UserRegistrationDto;
import cs302.project.student_web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserModel registerUser(UserRegistrationDto registrationDto) throws UserRegistrationException {
        // Validate username
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new UserRegistrationException("Username already exists");
        }

        // Validate email
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new UserRegistrationException("Email already exists");
        }

        // Validate password match
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            throw new UserRegistrationException("Passwords do not match");
        }

        // Create new user
        UserModel user = new UserModel();
        user.setUsername(registrationDto.getUsername());
//        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setPassword(registrationDto.getPassword());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());

        return userRepository.save(user);
    }

    public boolean authenticateUser(String username, String rawPassword) {
        return userRepository.findByUsername(username).map(user -> rawPassword.equals(user.getPassword())).orElse(false);
    }
}
