package br.com.sura.api.repositories;

import br.com.sura.api.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<List<Categoria>> findByNomeCategoriaIgnoreCaseContaining(String nomeCategoria);
}
