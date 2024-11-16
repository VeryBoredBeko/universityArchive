package com.satbayevUniversity.universityArchive.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


//@Controller
//@RequestMapping("/records/{recordId}/comments")
public class CommentController {
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private RecordRepository recordRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping("/add")
//    public String addComment(@PathVariable Long recordId,
//                                 @ModelAttribute("commentForm") CommentForm commentForm,
//                                Principal principal) {
//
//        commentRepository.save(commentForm.toComment());
//
//        return "redirect:/records/" + recordId;
//    }
}
