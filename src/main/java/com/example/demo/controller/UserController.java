package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	//test

    private final UserRepository userRepository;

    private final UserService service; 

    public UserController(UserService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }


    
    @PostMapping("/register")
    public String register(
            @RequestParam(name="username") String username,
            @RequestParam(name="password") String password) {
        return service.register(username, password);
    }
    
    

    // Login user
    @PostMapping("/login")
    public String login(
            @RequestParam(name="username") String username,
            @RequestParam(name="password") String password) {
        return service.login(username, password);
    }

    @Autowired
    private UserRepository repo; // repository for save

    @PostMapping(value="/{id}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadPhoto(
            @PathVariable(name="id") Long id,
            @RequestParam(name="file") MultipartFile file) throws IOException {
		
        if(file == null || file.isEmpty()) {
            return "Please choose a file!";
        }

        return service.uploadPhoto(id, file);
    }
    
    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getPhoto(@PathVariable(name="id") Long id) {
        byte[] photo = service.getPhoto(id);
        if(photo == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(photo);
    }
    
    
    
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") Long id) {
        User file = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ File not found with id: " + id));

        String fileType = file.getFileType();
        if (fileType == null || fileType.isBlank()) {
            fileType = "application/octet-stream"; // fallback
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getPhoto());
    }




}
