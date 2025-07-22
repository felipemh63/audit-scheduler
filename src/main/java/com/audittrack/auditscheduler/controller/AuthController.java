//package com.audittrack.auditscheduler.controller;

//import com.audittrack.auditscheduler.dto.AuthRequest;
//import com.audittrack.auditscheduler.security.JwtUtil;
//import com.audittrack.auditscheduler.security.MyUserDetailsService;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.security.authentication.*;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;

//import java.util.Map;

//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {

  //  @Autowired
    //private AuthenticationManager authenticationManager;

    //@Autowired
    //private MyUserDetailsService userDetailsService;

    //@Autowired
    //private JwtUtil jwtUtil;

    //@PostMapping("/login")
    //public ResponseEntity<?> login(@RequestBody AuthRequest request) {
      //  try {
            // Autenticar credenciales
        //    authenticationManager.authenticate(
          //      new UsernamePasswordAuthenticationToken(
            //        request.getUsername(), request.getPassword()
              //  )
           // );

            // Si pasa la autenticación, se genera el token JWT
          //  final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            //final String jwt = jwtUtil.generateToken(userDetails.getUsername());

            // Se devuelve el token como respuesta
            //return ResponseEntity.ok(Map.of("token", jwt));
        //} catch (BadCredentialsException e) {
          //  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
       // } catch (Exception e) {
           // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        //}
   // }
//}
