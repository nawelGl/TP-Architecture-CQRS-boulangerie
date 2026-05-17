package fr.upec.episen.grid.synchro_worker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    private String typePain;
    private int stockActuel;

    public Stock() {
    }

    public String getTypePain() {
        return typePain;
    }

    public void setTypePain(String typePain) {
        this.typePain = typePain;
    }

    public int getStockActuel() {
        return stockActuel;
    }

    public void setStockActuel(int stockActuel) {
        this.stockActuel = stockActuel;
    }
}