package br.com.sura.api.repositories;

import br.com.sura.api.model.PedidoItem;
import br.com.sura.api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

}
