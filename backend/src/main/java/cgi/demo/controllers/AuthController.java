package cgi.demo.controllers;

import cgi.demo.DTO.LoginDTO;
import cgi.demo.services.JwtService;
import cgi.demo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import cgi.demo.DTO.RegisterDTO;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO dto){
        return userService.registerNewUser(dto);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO dto){
        return userService.loginUser(dto);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(HttpServletRequest request){
        return ResponseEntity.ok(SecurityContextHolder.getContext());
    }

}
