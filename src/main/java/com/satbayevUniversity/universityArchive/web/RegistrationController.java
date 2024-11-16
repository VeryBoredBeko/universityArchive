package com.satbayevUniversity.universityArchive.web;

import com.satbayevUniversity.universityArchive.domain.RegistrationForm;
import com.satbayevUniversity.universityArchive.domain.User;
import com.satbayevUniversity.universityArchive.domain.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());

        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid @ModelAttribute("registrationForm") RegistrationForm form,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "registration";

        User user = form.toUser(passwordEncoder);
        userRepository.save(user);

        return "redirect:/login";
    }
}
