package com.imax.stock.bl.domain.dto;

import com.imax.stock.bl.domain.model.StockModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class StockDtoIn extends StockPriceDtoEditIn {
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String name;

    public StockDtoIn(StockModel model) {
        super(model);
        this.name = model.getName();
    }
}
