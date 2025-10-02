package com.senai.crud_usuarios.controllers;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.senai.crud_usuarios.models.UsuarioModel;
import com.senai.crud_usuarios.services.UsuarioService;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String viewLogin(@RequestParam(value = "erro", required = false) String erro, Model model) {
        if (erro != null) {
            model.addAttribute("erro", erro);
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email, @RequestParam String senha, HttpSession session, org.springframework.ui.Model model) {
        Optional<UsuarioModel> usuario = usuarioService.autenticar(email, senha);
        if (usuario.isPresent()) {
            // salva informações mínimas na sessão
            session.setAttribute("usuarioId", usuario.get().getIdUsuario());
            session.setAttribute("usuarioNome", usuario.get().getNomeUsuario());
            // em vez de redirecionar, retorna a própria view com mensagem de sucesso
            model.addAttribute("sucesso", "Login sucesso!");
            return "login";
        } else {
            model.addAttribute("erro", "Credenciais inválidas");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
