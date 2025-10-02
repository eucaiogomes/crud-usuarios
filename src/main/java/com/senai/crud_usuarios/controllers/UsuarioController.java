package com.senai.crud_usuarios.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.senai.crud_usuarios.dtos.RequisicaoDto;
import com.senai.crud_usuarios.dtos.RespostaDto;
import com.senai.crud_usuarios.models.UsuarioModel;
import com.senai.crud_usuarios.services.UsuarioService;

@Controller
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // Criar usuário
    @PostMapping
    public ResponseEntity<RespostaDto> cadastrarUsuario(@Valid @RequestBody RequisicaoDto dados) {
        RespostaDto resposta = service.cadastrarUsuarios(dados);
        return ResponseEntity.status(resposta.getCodigo() == 0 ? HttpStatus.OK : HttpStatus.valueOf(resposta.getCodigo())).body(resposta);
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listarUsuarios() {
        return ResponseEntity.ok(service.listarUsuarios());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        return service.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: usuário não encontrado."));
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<RespostaDto> atualizarUsuario(@PathVariable int id, @Valid @RequestBody RequisicaoDto dados) {
        RespostaDto resposta = service.atualizarUsuario(id, dados);
        return ResponseEntity.status(resposta.getCodigo() == 0 ? HttpStatus.OK : HttpStatus.valueOf(resposta.getCodigo())).body(resposta);
    }

    // Remover
    @DeleteMapping("/{id}")
    public ResponseEntity<RespostaDto> removerUsuario(@PathVariable int id) {
        RespostaDto resposta = service.removerUsuario(id);
        return ResponseEntity.status(resposta.getCodigo() == 0 ? HttpStatus.OK : HttpStatus.valueOf(resposta.getCodigo())).body(resposta);
    }

    // Obter usuario como DTO
    @GetMapping("/{id}/dto")
    public ResponseEntity<?> obterUsuario(@PathVariable int id){
        return service.obterUsuario(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: usuário não encontrado."));
    }
 
}
