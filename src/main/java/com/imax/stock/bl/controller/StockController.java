package com.imax.stock.bl.controller;

import com.imax.stock.bl.constants.StockRestApi;
import com.imax.stock.bl.domain.dto.PageableFilter;
import com.imax.stock.bl.domain.dto.StockDtoIn;
import com.imax.stock.bl.domain.dto.StockDtoOut;
import com.imax.stock.bl.domain.dto.StockPriceDtoEditIn;
import com.imax.stock.bl.service.StockService;
import com.imax.stock.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "Stock Controller")
@RestController
@Validated
public class StockController {

    private final StockService service;

    @Autowired
    public StockController(StockService service) {
        this.service = service;
    }


    @Operation(summary = "List Stocks With Filter And Pageable")
    @GetMapping(path = StockRestApi.STOCKS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StockDtoOut>> getAll(@Valid PageableFilter filter, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws CustomException{
        return new ResponseEntity<>(service.getAll(filter), HttpStatus.OK);
    }

    @Operation(summary = "Get Stock With Id")
    @GetMapping(path = StockRestApi.STOCKS_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDtoOut> getById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws CustomException {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @Operation(summary = "Create Stock With Data As Post Body")
    @PostMapping(path = StockRestApi.STOCKS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDtoOut> create(@Valid @RequestBody StockDtoIn dto, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @Operation(summary = "Update Stock By Id And With Data As Put Body")
    @PatchMapping(path = StockRestApi.STOCKS_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDtoOut> update(@Valid @RequestBody StockPriceDtoEditIn dto, BindingResult bindingResult, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws CustomException {
        return new ResponseEntity<>(service.updatePrice(id, dto), HttpStatus.OK);
    }

    @Operation(summary = "Delete Stock With Id")
    @DeleteMapping(path = StockRestApi.STOCKS_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws CustomException {
        return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
    }

}
