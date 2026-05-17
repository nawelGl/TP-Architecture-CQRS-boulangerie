package fr.upec.episen.grid.synchro_worker.repository;

import fr.upec.episen.grid.synchro_worker.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

}