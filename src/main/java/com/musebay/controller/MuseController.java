package com.musebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.musebay.model.Submission;
import com.musebay.repository.SubmissionRepository;

@Controller
public class MuseController {

    @Autowired
    private SubmissionRepository submissionRepository;

    // 👉 GET: 신청 폼 보여주기
    @GetMapping("/become")
    public String showBecomeForm() {
        return "become";
    }

    // 👉 Thank You 페이지
    @GetMapping("/thankyou")
    public String showThankYouPage() {
        System.out.println("✅ /thankyou 페이지로 이동함");
        return "thankyou";
    }

}
