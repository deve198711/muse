// AdminController.java
package com.musebay.controller;

import com.musebay.model.Submission;
import com.musebay.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Sort;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.mail.MessagingException; // ✅ 이거도 이메일 전송 시 예외 처리용
import java.util.Optional;
import com.musebay.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;


@Controller
public class AdminController {

    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private EmailService emailService;


    @GetMapping("/admin")
    public String showAdminDashboard(Model model) {
        List<Submission> submissions = submissionRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        model.addAttribute("submissions", submissions);
        return "admin";
    }

    @PostMapping("/admin/approve")
    public String approveSubmission(@RequestParam String email, Model model) {
        Optional<Submission> submissionOpt = submissionRepository.findByEmail(email);

        if (submissionOpt.isEmpty()) {
            model.addAttribute("error", "Submission not found");
            return "redirect:/admin";
        }

        Submission submission = submissionOpt.get();

        String generatedId = UUID.randomUUID().toString().substring(0, 8);
        String generatedPassword = UUID.randomUUID().toString().substring(0, 8);

        try {
            emailService.sendLoginInfoEmail(submission.getEmail(), generatedId, generatedPassword);
            model.addAttribute("message", "✅ 로그인 정보 이메일 전송 완료!");
        } catch (Exception e) {
            model.addAttribute("error", "❌ 이메일 전송 실패: " + e.getMessage());
        }

        return "redirect:/admin";
    }

}