package com.senai.crud_usuarios.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.senai.crud_usuarios.dtos.RequisicaoDto;
import com.senai.crud_usuarios.dtos.RespostaDto;
import com.senai.crud_usuarios.models.UsuarioModel;

@Service
public class UsuarioService {

    private final AtomicInteger ultimoId = new AtomicInteger(0);
    private final List<UsuarioModel> listaUsuarios = new ArrayList<>();

    // Cadastrar
    public RespostaDto cadastrarUsuarios(RequisicaoDto usuarioDto) {
        RespostaDto respostaDto = new RespostaDto();

        // Validações básicas (mais completas são feitas via @Valid no controller)
        if (usuarioDto.getEmail() == null || usuarioDto.getEmail().isBlank()) {
            respostaDto.setMensagem("Erro: email inválido.");
            respostaDto.setCodigo(HttpStatus.BAD_REQUEST.value());
            return respostaDto;
        }

        // Verifica duplicidade de email
        for (UsuarioModel usuario : listaUsuarios) {
            if (usuario.getEmailUsuario().equalsIgnoreCase(usuarioDto.getEmail())) {
                respostaDto.setMensagem("Erro: email já existe na base.");
                respostaDto.setCodigo(HttpStatus.CONFLICT.value());
                return respostaDto;
            }
        }

        int novoId = ultimoId.incrementAndGet();
        UsuarioModel usuario = new UsuarioModel();
        usuario.setIdUsuario(novoId);
        usuario.setNomeUsuario(usuarioDto.getNome());
        usuario.setEmailUsuario(usuarioDto.getEmail());
        usuario.setSenhaUsuario(usuarioDto.getSenha());

        listaUsuarios.add(usuario);

        respostaDto.setMensagem("Usuário cadastrado com sucesso!");
        respostaDto.setCodigo(HttpStatus.CREATED.value());
        return respostaDto;
    }

    // Listar todos (retorna cópia defensiva)
    public List<UsuarioModel> listarUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }

    // Buscar por ID
    public Optional<UsuarioModel> buscarPorId(int id) {
        return listaUsuarios.stream().filter(u -> u.getIdUsuario() == id).findFirst();
    }

    // Atualizar
    public RespostaDto atualizarUsuario(int id, RequisicaoDto dados) {
        RespostaDto resposta = new RespostaDto();

        if (dados.getEmail() == null || dados.getEmail().isBlank()) {
            resposta.setMensagem("Erro: email inválido.");
            resposta.setCodigo(HttpStatus.BAD_REQUEST.value());
            return resposta;
        }

        for (UsuarioModel usuario : listaUsuarios) {
            // Se o email já existe em outro usuário, bloqueia
            if (usuario.getEmailUsuario().equalsIgnoreCase(dados.getEmail())
                    && usuario.getIdUsuario() != id) {
                resposta.setMensagem("Erro: já existe um usuário com esse email.");
                resposta.setCodigo(HttpStatus.CONFLICT.value());
                return resposta;
            }
        }

        for (UsuarioModel usuario : listaUsuarios) {
            if (usuario.getIdUsuario() == id) {
                usuario.setNomeUsuario(dados.getNome());
                usuario.setEmailUsuario(dados.getEmail());
                usuario.setSenhaUsuario(dados.getSenha());
                resposta.setMensagem("Usuário atualizado com sucesso!");
                resposta.setCodigo(HttpStatus.OK.value());
                return resposta;
            }
        }

        resposta.setMensagem("Erro: usuário não encontrado.");
        resposta.setCodigo(HttpStatus.NOT_FOUND.value());
        return resposta;
    }

    // Remover
    public RespostaDto removerUsuario(int id) {
        RespostaDto resposta = new RespostaDto();

        Optional<UsuarioModel> usuarioEncontrado = buscarPorId(id);

        if (usuarioEncontrado.isPresent()) {
            listaUsuarios.remove(usuarioEncontrado.get());
            resposta.setMensagem("Usuário removido com sucesso!");
            resposta.setCodigo(HttpStatus.OK.value());
        } else {
            resposta.setMensagem("Erro: usuário não encontrado.");
            resposta.setCodigo(HttpStatus.NOT_FOUND.value());
        }

        return resposta;
    }

    public Optional<RequisicaoDto> obterUsuario(int id){
        return buscarPorId(id).map(u -> {
            RequisicaoDto usuarioDto = new RequisicaoDto();
            usuarioDto.setIdUsuario(u.getIdUsuario());
            usuarioDto.setNome(u.getNomeUsuario());
            usuarioDto.setEmail(u.getEmailUsuario());
            usuarioDto.setSenha(u.getSenhaUsuario());
            return usuarioDto;
        });
    }
}
