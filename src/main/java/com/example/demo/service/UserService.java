package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // Register user (without photo)
    public String register(String username, String password) {
        if (repo.findByUsername(username).isPresent()) {
            return " Username already exists!";
        }
        User user = new User(username, password);
        repo.save(user);
        return " User registered: " + username;
    }

    // Login user
    public String login(String username, String password) {
        Optional<User> user = repo.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return " Login successful! User ID: " + user.get().getId();
        }
        return " Invalid username or password!";
    }

    // Upload photo for user
    public String uploadPhoto(Long userId, MultipartFile file) throws IOException {
        Optional<User> userOpt = repo.findById(userId);
        if (userOpt.isEmpty()) return "❌ User not found!";

        User user = userOpt.get();
        user.setPhoto(file.getBytes());
        repo.save(user);
        return " Photo uploaded for user: " + user.getUsername();
    }

    // Get photo
    public byte[] getPhoto(Long userId) {
        return repo.findById(userId).map(User::getPhoto).orElse(null);
    }
    
    // downlod photo 
    
    public User getFile(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id " + id));
    }

	public Optional<User> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
