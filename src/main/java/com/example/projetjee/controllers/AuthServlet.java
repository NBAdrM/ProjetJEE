package com.example.projetjee.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Authentification réussie !");
    }

    @RestController
    @RequestMapping("/auth")
    public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/roles")
    public ResponseEntity<String> getUserRoles(@RequestParam String token) {
        String roles = authService.getRolesByToken(token);
        return ResponseEntity.ok(roles);
    }
}

    
}
