package com.imax.stock.bl.domain.dto;

import com.imax.stock.bl.domain.model.StockModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class StockPriceDtoEditIn {
    @NotNull
    private BigDecimal currentPrice;

    public StockPriceDtoEditIn(StockModel model) {
        if (model != null) {
            this.currentPrice = model.getCurrentPrice();
        }
    }
}
