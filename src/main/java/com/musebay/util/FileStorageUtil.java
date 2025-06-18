package com.musebay.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FileStorageUtil {

    public static String sanitizeEmail(String email) {
        return email.replaceAll("[^a-zA-Z0-9]", "_");
    }

    // ✅ 단일 파일 저장 (파일명 안전 처리)
    public static String saveSingleFile(MultipartFile file, String directory, String prefix) throws IOException {
        if (file == null || file.isEmpty()) return null;

        // ✅ 파일명 정리: 한글, 공백, 특수문자 제거
        String originalName = file.getOriginalFilename();
        String safeFilename = java.text.Normalizer
                .normalize(originalName, java.text.Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")                  // 한글 등 유니코드 제거
                .replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");          // 남은 특수문자 제거

        String filename = prefix + "_" + UUID.randomUUID() + "_" + safeFilename;

        // ✅ 저장 디렉토리 준비
        Path dirPath = Paths.get("uploads", directory);
        Files.createDirectories(dirPath);

        // ✅ 실제 저장
        Path fullPath = dirPath.resolve(filename);
        System.out.println("📄 original name: " + file.getOriginalFilename());
        System.out.println("📂 saving to: " + fullPath.toString());
        if (!file.isEmpty()) {
            System.out.println("✅ file is not empty, trying to save...");
        } else {
            System.out.println("❌ file is EMPTY or missing in temp location.");
        }

        file.transferTo(fullPath.toFile());

        return "uploads/" + directory + "/" + filename;
    }

    // ✅ 여러 파일 저장
    public static List<String> saveMultipleFiles(MultipartFile[] files, String directory) throws IOException {
        List<String> savedPaths = new ArrayList<>();
        if (files == null) return savedPaths;

        for (MultipartFile file : files) {
            String saved = saveSingleFile(file, directory, "media");
            if (saved != null) savedPaths.add(saved);
        }
        return savedPaths;
    }
}
