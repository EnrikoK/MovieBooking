package cgi.demo.services;

import cgi.demo.DTO.LoginDTO;
import cgi.demo.DTO.RegisterDTO;
import cgi.demo.entities.Role;
import cgi.demo.entities.User;
import cgi.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtService jwtService;

    public ResponseEntity<?> registerNewUser(RegisterDTO dto) {

        User userExists = repository.findByUsername(dto.getUsername());
        if(userExists == null){
            User user = new User();
            user.setPassword(encoder.encode(dto.getPassword()));
            user.setUsername(dto.getUsername());
            user.setRole(Role.USER);
            repository.saveAndFlush(user);
            return ResponseEntity.ok().body(Map.of("message","success"));

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message","User with that username already exists"));
        }
    }

    public ResponseEntity<?> loginUser(LoginDTO dto) {

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

            Object principal = authentication.getPrincipal();
            UserDetails user = (UserDetails) principal;

            String token = jwtService.generateToken(user.getUsername(),jwtService.cookieExpiry);

            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .path("/")
                    .build();

            HttpHeaders jwtCookieHeader = new HttpHeaders();
            jwtCookieHeader.add(HttpHeaders.SET_COOKIE,cookie.toString());

            // Return a success response
            return ResponseEntity.ok().headers(jwtCookieHeader).body(Map.of("authentication",true,"username",authentication.getName()));
        }catch (Exception e){

            return ResponseEntity.status(401).body(Map.of("message","Wrong username or password"));
        }

    }

    public ResponseEntity<?> logoutUser(String token){
        try{
            String logoutToken = jwtService.setTokenExpiryToLogout(token);
            ResponseCookie cookie = ResponseCookie.from("jwt", logoutToken)
                    .httpOnly(true)
                    .path("/")
                    .build();

            HttpHeaders jwtCookieHeader = new HttpHeaders();
            jwtCookieHeader.add(HttpHeaders.SET_COOKIE,cookie.toString());
            return ResponseEntity.status(200).headers(jwtCookieHeader).build();
        } catch (Exception e){
            return ResponseEntity.status(400).body(Map.of("message","Something went wrong when logging out"));
        }
    }


}
