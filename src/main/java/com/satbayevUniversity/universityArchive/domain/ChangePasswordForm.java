package com.satbayevUniversity.universityArchive.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordForm {

    @NotBlank
    private String currentPassword;

    @NotBlank
    @Size(min = 8, max = 32, message = "Длина пароля от 8 до 32")
    private String newPassword;

    @NotBlank
    private String confirmPassword;
}
