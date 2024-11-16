package com.satbayevUniversity.universityArchive.web;

import com.satbayevUniversity.universityArchive.domain.ChangePasswordForm;
import com.satbayevUniversity.universityArchive.domain.User;
import com.satbayevUniversity.universityArchive.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Көптеген қажетті импорттар

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping({"", "/"})
    public String showProfile(Model model, Principal principal) {

        // Функция құрамындағы код

        model.addAttribute("user", userService.findByEmail(principal.getName()));

        if (!model.containsAttribute("changePasswordForm")) {
            model.addAttribute("changePasswordForm", new ChangePasswordForm());
        }

        return "user/profile";
    }

    @PostMapping("/changePassword")
    public String changePassword(@Valid @ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm,
                                 BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordForm", bindingResult);
            redirectAttributes.addFlashAttribute("changePasswordForm", changePasswordForm);
            return "redirect:/profile";
        }

        try {
            User user = userService.findByEmail(principal.getName());

            if (!passwordEncoder.matches(changePasswordForm.getCurrentPassword(), user.getPassword())) {
                redirectAttributes.addFlashAttribute("message", "Введите корректный пароль");
                return "redirect:/profile";
            }

            if (!changePasswordForm.getNewPassword().equals(changePasswordForm.getConfirmPassword())) {
                redirectAttributes.addFlashAttribute("message", "Новый пароль не совпадает");
                return "redirect:/profile";
            }

            // құпиясөздерді сәйкестікке тексеру...

            user.setPasswordHash(passwordEncoder.encode(changePasswordForm.getNewPassword()));
            userService.save(user);

            redirectAttributes.addFlashAttribute("message", "Пароль успешно изменен");
            return "redirect:/profile";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Неизвестная ошибка");
            return "redirect:/profile";
        }
    }
}
