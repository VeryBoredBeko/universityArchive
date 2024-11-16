package com.satbayevUniversity.universityArchive.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

    @NotBlank(message = "Поле для имени не может быть пустым")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "Поле для фвмилии не может быть пустым")
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank(message = "Поле для почты не может быть пустым")
    private String email;

    @NotBlank(message = "Поле для пароля не может быть пустым")
    @Size(min = 8, max = 32, message = "Пароль должен состоять минимально из 8 символов")
    private String passwordHash;

    public User toUser(PasswordEncoder passwordEncoder) {
        // TODO: Надо изменить Role на подходящюю логику!
        return new User (
                firstName, lastName, email, passwordEncoder.encode(passwordHash), "Employee"
        );
    }
}
