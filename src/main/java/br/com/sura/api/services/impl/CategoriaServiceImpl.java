package br.com.sura.api.services.impl;

import br.com.sura.api.model.Categoria;
import br.com.sura.api.repositories.CategoriaRepository;
import br.com.sura.api.services.CategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Optional<List<Categoria>> buscarPorCategoria(String categoria) {
       log.info("Buscando Categoria pela Categoria {}", categoria);
      return categoriaRepository.findByNomeCategoriaIgnoreCaseContaining(categoria);
    }

    @Override
    public Optional<Categoria> buscarPorId(Long id) {
        log.info("Buscando Categoria pelo Id {}", id);
        return this.categoriaRepository.findById(id);
    }

    @Override
    public Categoria persistir(Categoria categoria) {
        return null;
    }

    @Override
    public List<Categoria> getCategorias() {
        log.info("Buscando todas Categorias");
        List<Categoria> listaCategoria = new ArrayList<>();
        categoriaRepository.findAll().forEach(listaCategoria::add);
        return listaCategoria;
    }

}
