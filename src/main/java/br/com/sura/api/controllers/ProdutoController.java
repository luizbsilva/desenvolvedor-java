package br.com.sura.api.controllers;

import br.com.sura.api.dtos.ClienteDTO;
import br.com.sura.api.dtos.ProdutoDTO;
import br.com.sura.api.model.Produto;
import br.com.sura.api.response.Response;
import br.com.sura.api.services.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private static final Logger log = LoggerFactory.getLogger(ProdutoController.class);


    @Autowired
    private ProdutoService produtoService;


    @GetMapping()
    public ResponseEntity<List<Produto>> listarClientes() {
        return ResponseEntity.ok(new ProdutoDTO().converterProduto(produtoService.getProdutos()));
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        Produto produto = produtoService.getByIdProdutos(id);

        if (produto != null) {
            return ResponseEntity.ok(produto);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Response<Produto>> persistir(@RequestBody @Valid ProdutoDTO produtoDto,
                                                       UriComponentsBuilder uriBuilder) {
        Response<Produto> response = new Response<>();
        Produto produto = produtoService.salvaProduto(produtoDto);
        response.setData(produto);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/produto/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody @Valid ProdutoDTO produtoDto) {
        Produto produto = produtoService.validarAtualizacao(id, produtoDto);

        Produto updateproduto = produtoService.alterar(produto);
        return ResponseEntity.ok(updateproduto);
    }

    @DeleteMapping("/produto/{id}")
    public ResponseEntity<ProdutoDTO> excluirProduto(@PathVariable Long id) {
        Produto produto = produtoService.getByIdProdutos(id);

        if (produto != null) {
            produtoService.deletar(produto.getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



}
