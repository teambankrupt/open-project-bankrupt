package com.example.virtualfilesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class VirtualFilesystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualFilesystemApplication.class, args);
    }

}
