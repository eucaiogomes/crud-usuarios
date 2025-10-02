package com.senai.crud_usuarios.dtos;

public class RespostaDto {
    private String mensagem;
    private int codigo;

    public RespostaDto() {
    }

    public RespostaDto(String mensagem) {
        this.mensagem = mensagem;
    }

    public RespostaDto(String mensagem, int codigo) {
        this.mensagem = mensagem;
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
