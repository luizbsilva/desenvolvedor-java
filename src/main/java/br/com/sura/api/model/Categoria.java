package br.com.sura.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categoria")
public class Categoria extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "categoria")
    private String nomeCategoria;

    @OneToMany(mappedBy = "categoria")
    private List<Produto> produtos = new ArrayList<>();

}
