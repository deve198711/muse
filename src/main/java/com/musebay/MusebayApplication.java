package com.musebay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusebayApplication {
	public static void main(String[] args) {
		System.out.println("🔴 앱 실행 시작됨");
		SpringApplication.run(MusebayApplication.class, args);
		System.out.println("✅ 앱 실행 완료됨");
	}
}
