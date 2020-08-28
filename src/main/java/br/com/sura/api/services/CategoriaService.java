package br.com.sura.api.services;


import br.com.sura.api.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

	/**
	 * Retorna uma categoria dado uma categoria.
	 * 
	 * @param categoria
	 * @return Optional<Categoria>
	 */
	Optional<List<Categoria>> buscarPorCategoria(String categoria);

	/**
	 * Retorna uma categoria dado um id.
	 *
	 * @param id
	 * @return Optional<Categoria>
	 */
	Optional<Categoria> buscarPorId(Long id);
	
	/**
	 * Cadastra uma nova Categoria na base de dados.
	 * 
	 * @param categoria
	 * @return Categoria
	 */
	Categoria persistir(Categoria categoria);

	/**
	 * Retorna todas as categorias.
	 *
	 * @return List<Categoria>
	 */
	List<Categoria> getCategorias();
	
}
