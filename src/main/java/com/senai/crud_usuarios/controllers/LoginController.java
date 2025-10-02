package com.senai.crud_usuarios.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //agora vamos falar com o navegador
public class LoginController {

    //metodo que vai retornar 

    @GetMapping("/login") //entrega a view do html
    public String viewLogin(){
        return "login"; //nome do arquivo html
    }
    //post de envio do formulario
    
}
