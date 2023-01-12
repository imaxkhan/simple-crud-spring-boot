package com.imax.stock.bl.domain.dto;

import com.imax.stock.bl.domain.model.StockModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class StockDtoOut extends StockDtoIn {
    private long id;
    private LocalDateTime lastUpdate;

    public StockDtoOut(StockModel model) {
        super(model);
        if (model != null) {
            this.id = model.getId();
            this.lastUpdate = model.getLastUpdate();
        }
    }
}
