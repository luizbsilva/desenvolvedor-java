package br.com.sura.api.controllers;

import br.com.sura.api.dtos.CategoriaDTO;
import br.com.sura.api.model.Categoria;
import br.com.sura.api.response.Response;
import br.com.sura.api.services.CategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categoria")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private static final Logger log = LoggerFactory.getLogger(CategoriaController.class);

    @Autowired
    private CategoriaService categoriaService;

    /**
     * Retorna uma Categoria dado um nome.
     *
     * @param nome
     * @return ResponseEntity<List <   CategoriaDTO>>
     */
    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<List<CategoriaDTO>> buscarPorCategoria(@PathVariable("nome") String nome) {
        log.info("Buscando Categoria por Nome: {}", nome);
        Optional<List<Categoria>> categorias = categoriaService.buscarPorCategoria(nome);

        if (categorias.isPresent())
            return ResponseEntity.ok(this.converterCategoriaDtos(categorias.get()));

        return ResponseEntity.notFound().build();

    }

    /**
     * Retorna todas Categorias cadastradas.
     *
     * @return ResponseEntity<List <CategoriaDTO>>
     */
    @GetMapping()
    public ResponseEntity<List<CategoriaDTO>> buscarTodasCategorias() {
        log.info("Buscando Todas Categorias");
        List<Categoria> categorias = categoriaService.getCategorias();

        if (categorias != null)
            return ResponseEntity.ok(this.converterCategoriaDtos(categorias));

        return ResponseEntity.notFound().build();
    }

    /**
     * Retorna uma Categoria por ID.
     *
     * @param id
     * @return ResponseEntity<Response   <   CategoriaDTO>>
     */
    @GetMapping("/categoria/{id}")
    public ResponseEntity<Response<CategoriaDTO>> buscaCategoriaPorId(@PathVariable(required = true) Long id) {
        log.info("Buscando Categoria por id: {}", id);
        Response<CategoriaDTO> response = new Response<>();
        Optional<Categoria> categoria = categoriaService.buscarPorId(id);

        if (!categoria.isPresent()) {
            log.info("Categoria não encontrada para o id: {}", id);
            response.getErrors().add("Categoria não encontrada para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.converterCategoriaDto(categoria.get()));
        return ResponseEntity.ok(response);
    }

    /**
     * Popula um DTO com os dados de uma empresa.
     *
     * @param categorias
     * @return CategoriaDTO
     */
    private List<CategoriaDTO> converterCategoriaDtos(List<Categoria> categorias) {
        List<CategoriaDTO> categoriaDTOList = new ArrayList<>();
        categorias.forEach(categoria -> categoriaDTOList.add(converterCategoriaDto(categoria)));

        return categoriaDTOList;
    }

    /**
     * Popula um DTO com os dados de uma Categoria.
     *
     * @param categoria
     * @return CategoriaDTO
     */
    private CategoriaDTO converterCategoriaDto(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNomeCategoria(categoria.getNomeCategoria());

        return categoriaDTO;
    }
}
