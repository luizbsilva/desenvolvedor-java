package br.com.sura.api.repositories;

import br.com.sura.api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<List<Pedido>> findByClienteEmail(String email);

    @Query("select ped from Pedido ped join ped.cliente cli where ped.id = :id and cli.email = :email")
    Optional<Pedido> findByPedidoAndCliente(@Param("id") Long id, @Param("email") String email);

}
