package br.com.drogabraia.domain.model;

import br.com.drogabraia.domain.enums.GenderEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, length = 10)
    private String dataNascimento;

    @Enumerated(EnumType.STRING)
    private GenderEnum genero;

    @Column(length = 8)
    private String cep;

    @Column(length = 5)
    private String numero;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dataCriacao;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}
