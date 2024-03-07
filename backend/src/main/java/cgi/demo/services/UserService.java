package cgi.demo.services;

import cgi.demo.DTO.LoginDTO;
import cgi.demo.DTO.RegisterDTO;
import cgi.demo.entities.Role;
import cgi.demo.entities.User;
import cgi.demo.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;
import java.util.Map;

@Service
public class UserService {

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

            //SecurityContextHolder.getContext().setAuthentication(authentication);
            Object prin = authentication.getPrincipal();
            UserDetails a = (UserDetails) prin;
            System.out.println(a);

            String token = jwtService.generateToken(dto.getUsername());

            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)  // Set HTTP-only attribute
                    .path("/") // Set cookie path
                    .build();

            HttpHeaders jwtCookieHeader = new HttpHeaders();
            jwtCookieHeader.add(HttpHeaders.SET_COOKIE,cookie.toString());


            // Return a success response
            return ResponseEntity.ok().headers(jwtCookieHeader).body("ok");
        }catch (Exception e){

            return ResponseEntity.status(400).body(Map.of("message",e.toString()));
        }

    }
}
