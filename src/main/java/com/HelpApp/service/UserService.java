package com.HelpApp.service;

import com.HelpApp.entity.User;
import com.HelpApp.repository.RoleRepository;
import com.HelpApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;

    public ResponseEntity<?> login(User user) { //вход
        Optional<User> userAttempt = userRepository.findByEmail(user.getEmail());
        if (userAttempt.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Ошибка! Пользователь не найден");
        } else if (!encoder.matches(user.getPassword(), userAttempt.get().getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("Ошибка! Неверный пароль");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication.isAuthenticated()) { //если всё ок
            return ResponseEntity.ok().body(userAttempt); //возвращаем пользователю
        } else {
            return ResponseEntity.status(403).build(); //или ошибку аутентификации
        }
    }

    public ResponseEntity<?> createUser(User user) { //регистрация пользователя
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Email уже зарегистрирован. Пользователь не создан.");
        }

        user.setPassword(encoder.encode(user.getPassword())); //шифруем пароль
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        userRepository.save(user); //сохраняем в бд
        return ResponseEntity.ok().body("");
    }

    public ResponseEntity<?> updateUser(User user) { //обновляем пользователя
        userRepository.save(user); //мерджим в бд
        return ResponseEntity.ok().body("");
    }

    public User getById(Long id) {
        return userRepository.findById(id).get();
    }

    public ResponseEntity<?> getUsers(String fts) {
        if (fts != null && !fts.isEmpty()) { //отфильтрованные по fts
            return ResponseEntity.ok().body(userRepository.findUsers().stream()
                    .filter(e -> e.getName().contains(fts) ||
                            e.getEmail().contains(fts) ||
                            e.getAbout().contains(fts) ||
                            e.getPhone().contains(fts)
                    )
                    .collect(Collectors.toList()));
        } else {
            return ResponseEntity.ok().body(userRepository.findUsers()); //или все
        }
    }
}