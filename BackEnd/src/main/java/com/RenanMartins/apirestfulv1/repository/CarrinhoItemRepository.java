package com.RenanMartins.apirestfulv1.repository;

import com.RenanMartins.apirestfulv1.model.CarrinhoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoItemRepository extends JpaRepository<CarrinhoItem, Long> {
}
