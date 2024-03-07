package cgi.demo.controllers;

import cgi.demo.DTO.LoginDTO;
import cgi.demo.services.JwtService;
import cgi.demo.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import cgi.demo.DTO.RegisterDTO;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    AuthService authService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO dto){
        return authService.registerNewUser(dto);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO dto){
        return authService.loginUser(dto);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@CookieValue(name = "jwt",defaultValue = "")String token){
        boolean isAuth = jwtService.validateToken(token);
        return ResponseEntity.ok(Map.of("authentication",isAuth));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(HttpServletRequest request){
        return ResponseEntity.ok(SecurityContextHolder.getContext());
    }

}
