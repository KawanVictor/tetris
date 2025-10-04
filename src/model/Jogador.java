package model;

import java.time.LocalDateTime;

public class Jogador {
    private String id, nome, email;
    private LocalDateTime dataCriacao;

    public Jogador(String id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataCriacao = LocalDateTime.now();
    }
    public Jogador(String id, String nome, String email, LocalDateTime dt) {
        this.id = id; this.nome = nome; this.email = email; this.dataCriacao = dt;
    }
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
}
