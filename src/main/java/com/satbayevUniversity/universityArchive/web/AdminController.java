package com.satbayevUniversity.universityArchive.web;

import com.satbayevUniversity.universityArchive.domain.User;
import com.satbayevUniversity.universityArchive.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @GetMapping({"", "/"})
    public String adminDashboard(Model model) {

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin/adminDashboard";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin/listUsers";
    }

    @GetMapping("/users/{id}/edit")
    public String editUserById(@PathVariable Long id, Model model) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            model.addAttribute("userId", id);
            return "fragments/changeUserRole :: changeUserRoleModal";
        }

        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/edit/save")
    public String saveEditedUserById(@PathVariable Long id, @RequestParam String role) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.setRole(role);
            userRepository.save(user);

            return "redirect:/admin/users";
        }

        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUserById(@PathVariable Long id) {
        userRepository.deleteById(id);

        return "redirect:/admin/users";
    }
}
