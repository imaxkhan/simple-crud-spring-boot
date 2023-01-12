package com.imax.stock.bl.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "STOCK")
@Getter
@Setter
@NoArgsConstructor
public class StockModel {
    @Id
    @Column(name = "ID_PK", scale = 0, precision = 19)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Stock_Sequence")
    @SequenceGenerator(name = "Stock_Sequence", sequenceName = "STOCK_SEQ", allocationSize = 10)
    private long id;
    @Column(name = "NAME", length = 255)
    private String name;
    @Column(name = "CURRENT_PRICE", scale = 8, precision = 18)
    private BigDecimal currentPrice;
    @Basic
    @Column(name = "LAST_UPDATE")
    private LocalDateTime lastUpdate;

    @PreUpdate
    public void preUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }

    public StockModel(String name, BigDecimal currentPrice) {
        this.name = name;
        this.currentPrice = currentPrice;
    }
}
