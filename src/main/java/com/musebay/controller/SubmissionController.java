package com.musebay.controller;

import com.musebay.model.Submission;
import com.musebay.repository.SubmissionRepository;
import com.musebay.util.FileStorageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class SubmissionController {

    @Autowired
    private SubmissionRepository submissionRepository;

    @PostMapping("/become")
    public String handleFormSubmission(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("birthdate") String birthdate,
            @RequestParam("profile") MultipartFile profileImage,
            @RequestParam(value = "id", required = false) MultipartFile idImage,
            @RequestParam(value = "media", required = false) MultipartFile[] mediaFiles,
            @RequestParam(value = "about", required = false) String about,
            Model model
    ) throws IOException {

        System.out.println("📂 TMP DIR = " + System.getProperty("java.io.tmpdir"));

        // ✅ 디버깅 로그
        System.out.println("✅ name: " + name);
        System.out.println("✅ profileImage filename: " + profileImage.getOriginalFilename());
        System.out.println("📂 profileImage.isEmpty(): " + profileImage.isEmpty());

        // ✅ 폴더 이름 (이메일 기반)
        String folder = FileStorageUtil.sanitizeEmail(email);

        // ✅ 파일 저장
        String profilePath = FileStorageUtil.saveSingleFile(profileImage, folder, "profile");
        String idPath = FileStorageUtil.saveSingleFile(idImage, folder, "id");
        List<String> mediaPaths = FileStorageUtil.saveMultipleFiles(mediaFiles, folder);

        // ✅ DB 저장
        Submission submission = Submission.builder()
                .name(name)
                .email(email)
                .birthdate(LocalDate.parse(birthdate))
                .profileImagePath(profilePath)
                .idImagePath(idPath)
                .mediaFiles(mediaPaths)
                .about(about)
                .build();

        submissionRepository.save(submission);
        model.addAttribute("message", "폼 제출 성공!");

        return "thankyou";
    }
}
