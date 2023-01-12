package com.imax.stock.bl.domain.dto;

import com.imax.stock.exception.CustomException;
import com.imax.stock.exception.IValidation;
import com.imax.stock.exception.SystemError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableFilter implements IValidation {
    private int page;
    private int size;

    @Override
    public void validate() throws CustomException {
        if (page < 0 || size <= 0) {
            throw new CustomException(SystemError.VALIDATION_EXCEPTION, "page and size value problem", 100258);
        }

    }
}
