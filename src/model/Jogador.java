package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Jogador {
    private final String id;
    private String nome;
    private String email;
    private LocalDateTime dataCriacao;

    public Jogador(String id, String nome, String email) {
        this.id = Objects.requireNonNull(id, "ID não pode ser null");
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser null");
        this.email = email;
        this.dataCriacao = LocalDateTime.now();
    }

    public Jogador(String id, String nome, String email, LocalDateTime dataCriacao) {
        this.id = Objects.requireNonNull(id, "ID não pode ser null");
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser null");
        this.email = email;
        this.dataCriacao = dataCriacao;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }

    public void alterarNome(String novoNome) {
        this.nome = Objects.requireNonNull(novoNome, "Nome não pode ser null");
    }

    public void alterarEmail(String novoEmail) {
        this.email = novoEmail;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Jogador) {
            Jogador outro = (Jogador) obj;
            return id.equals(outro.id);
        }
        return false;
    }

    @Override
    public int hashCode() { return id.hashCode(); }

    @Override
    public String toString() {
        return String.format("Jogador{id='%s', nome='%s', email='%s'}", id, nome, email);
    }
}
