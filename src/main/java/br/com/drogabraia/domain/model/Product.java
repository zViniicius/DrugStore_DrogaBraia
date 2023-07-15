package br.com.drogabraia.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private double preco;
    private double desconto;
    private String marca;
    private String imagem;
    private String tipoProduto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer fabricante;

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

    @Data
    @NoArgsConstructor
    @Entity
    @EqualsAndHashCode(callSuper = true)
    @DiscriminatorValue("MEDICAMENTO")
    public static class Medicament extends Product {
        private String classificacao;
        private String viaAdministracao;
        private String principioAtivo;
    }

    @Data
    @NoArgsConstructor
    @Entity
    @EqualsAndHashCode(callSuper = true)
    @DiscriminatorValue("PERFUMARIA")
    public static class Beauty extends Product {
        private String indicacao;
    }
}
