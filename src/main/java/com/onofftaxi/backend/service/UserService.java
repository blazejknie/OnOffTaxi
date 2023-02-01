package com.onofftaxi.backend.service;

import com.onofftaxi.backend.exception.NotFoundException;
import com.onofftaxi.backend.model.CustomUserDetails;
import com.onofftaxi.backend.model.User;
import com.onofftaxi.backend.repositories.DriverRepository;
import com.onofftaxi.backend.repositories.UserRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    DriverRepository driverRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        long driverId;
        String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }
        Optional<User> optionalUser = userRepository.findByLogin(email);
        if (!optionalUser.isPresent()) {
//            optionalUser = userRepository.findByLogin(email);
            try {
                driverId = driverRepository.findByEmail(email).getId();
                optionalUser = userRepository.findById(driverId);
            } catch (Exception e) {
                System.out.println("nieudane logowanie || " + e.getCause() + " : " + e.getMessage() + " :: " + LocalDateTime.now());
            }
        }
        optionalUser
                .orElseThrow(() -> new UsernameNotFoundException("email not found"));
        return optionalUser
                .map(CustomUserDetails::new).get();
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getOneById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User", id));
    }

    public List<User> getByName(String name) {
        return userRepository.searchByName(name);
    }

    public User getByLoginAndPassword(String email, String password) {
        return userRepository.getUserByLoginAndPassword(email, password);
    }

    public String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    public User getByPassword(String password) {
        return userRepository.getUserByPassword(password);
    }

    public User getByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    private static Map<Long, String> rememberedUsers = new HashMap<>();

    public boolean isAuthenticUser(String username) {
        User user = getByLogin(username);
        return user != null;
    }

    public static Long rememberUser(User user) {
        rememberedUsers.put(user.getId(), user.getLogin());
        return user.getId();
    }

    public static String getRememberedUser(Long id) {
        return rememberedUsers.get(id);
    }

    public static void removeRememberedUser(Long id) {
        rememberedUsers.remove(id);
    }
}
