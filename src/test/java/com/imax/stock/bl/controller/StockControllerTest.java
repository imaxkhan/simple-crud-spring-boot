package com.imax.stock.bl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imax.stock.bl.domain.dto.PageableFilter;
import com.imax.stock.bl.domain.dto.StockDtoIn;
import com.imax.stock.bl.domain.dto.StockDtoOut;
import com.imax.stock.bl.domain.dto.StockPriceDtoEditIn;
import com.imax.stock.bl.domain.model.StockModel;
import com.imax.stock.bl.service.StockService;
import com.imax.stock.exception.CustomException;
import com.imax.stock.exception.SystemError;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@OverrideAutoConfiguration(enabled = true)
@WebMvcTest(StockController.class)
class StockControllerTest {
    @MockBean
    private StockService stockService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenListOfStocks_whenGetAllStocks_thenReturnStockList() throws Exception {
        // given - precondition or setup
        List<StockDtoOut> stockDtoBucket = new ArrayList<>();
        StockDtoOut stockDtoOut1 = new StockDtoOut();
        stockDtoOut1.setId(1);
        stockDtoOut1.setName("coca");
        stockDtoOut1.setCurrentPrice(BigDecimal.valueOf(16000));
        stockDtoOut1.setLastUpdate(LocalDateTime.now());

        StockDtoOut stockDtoOut2 = new StockDtoOut();
        stockDtoOut2.setId(2);
        stockDtoOut2.setName("BMW");
        stockDtoOut2.setCurrentPrice(BigDecimal.valueOf(9800000));
        stockDtoOut2.setLastUpdate(LocalDateTime.now().plusDays(2));

        stockDtoBucket.add(stockDtoOut1);
        stockDtoBucket.add(stockDtoOut2);

        when(stockService.getAll(any(PageableFilter.class))).thenReturn(stockDtoBucket);

        ResultActions response = mockMvc.perform(get("/stocks?size=2&page=1"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(stockDtoBucket.size())));

    }

    @Test
    public void givenStockId_whenGetStockById_thenReturnStockDtoOutObjectOnSuccess() throws Exception {
        long stockId = 1L;
        StockDtoOut stockDtoOut = new StockDtoOut();
        stockDtoOut.setId(stockId);
        stockDtoOut.setName("coca");
        stockDtoOut.setLastUpdate(LocalDateTime.now());
        stockDtoOut.setCurrentPrice(BigDecimal.valueOf(15000));

        when(stockService.getById(any(Long.class))).thenReturn(stockDtoOut);

        ResultActions response = mockMvc.perform(get("/stocks/{id}", stockId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(stockDtoOut.getId()))
                .andExpect(jsonPath("$.name").value(stockDtoOut.getName()))
                .andExpect(jsonPath("$.currentPrice").value(stockDtoOut.getCurrentPrice()))
                .andExpect(jsonPath("$.lastUpdate").value(stockDtoOut.getLastUpdate().toString()));

    }


    @Test
    public void givenInvalidStockId_whenGetStockById_thenReturnNotFound() throws Exception {
        long stockId = 2L;
        StockDtoOut stockDtoOut = new StockDtoOut();
        stockDtoOut.setId(stockId);
        stockDtoOut.setName("coca");
        stockDtoOut.setLastUpdate(LocalDateTime.now());
        stockDtoOut.setCurrentPrice(BigDecimal.valueOf(15000));

        when(stockService.getById(1L)).thenThrow(new CustomException(SystemError.DATA_NOT_FOUND, "data not found", 10024));

        ResultActions response = mockMvc.perform(get("/stocks/{id}", 1L));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void givenStockODto_whenCreateStockModel_thenReturnSavedStockModel() throws Exception {

        StockDtoIn stockDtoIn = new StockDtoIn();
        stockDtoIn.setName("dummy");
        stockDtoIn.setCurrentPrice(BigDecimal.valueOf(1500000));

        StockDtoOut stockDtoOut = new StockDtoOut();
        stockDtoOut.setName(stockDtoIn.getName());
        stockDtoOut.setLastUpdate(null);
        stockDtoOut.setCurrentPrice(stockDtoIn.getCurrentPrice());

        when(stockService.create(any(StockDtoIn.class))).thenReturn(stockDtoOut);

        ResultActions response = mockMvc.perform(post("/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stockDtoIn)));

        response
                .andDo(print()).
                andDo(System.out::println)
                .andExpect(status().isOk())
                .andDo(System.out::println)
                .andExpect(jsonPath("$.name").value(stockDtoOut.getName()))
                .andExpect(jsonPath("$.currentPrice").value(stockDtoOut.getCurrentPrice()))
                .andExpect(jsonPath("$.lastUpdate").value(IsNull.nullValue()));

    }

    @Test
    public void givenUpdatedStockPrice_whenUpdateStockPrice_thenReturnUpdateStockObject() throws Exception {
        // given - precondition or setup
        LocalDateTime now = LocalDateTime.now();
        long stockId = 1L;
        StockModel savedStock = new StockModel();
        savedStock.setId(1);
        savedStock.setName("Dummy");
        savedStock.setLastUpdate(null);
        savedStock.setCurrentPrice(BigDecimal.valueOf(15000));


        StockPriceDtoEditIn updatedStockPrice = new StockPriceDtoEditIn();
        updatedStockPrice.setCurrentPrice(BigDecimal.valueOf(149000));

        StockDtoOut stockDtoOutBeforeUpdate = new StockDtoOut();
        stockDtoOutBeforeUpdate.setId(stockId);
        stockDtoOutBeforeUpdate.setName(savedStock.getName());
        stockDtoOutBeforeUpdate.setCurrentPrice(savedStock.getCurrentPrice());
        stockDtoOutBeforeUpdate.setLastUpdate(null);

        StockDtoOut stockDtoOutAfterUpdate = new StockDtoOut();
        stockDtoOutAfterUpdate.setId(stockId);
        stockDtoOutAfterUpdate.setName(savedStock.getName());
        stockDtoOutAfterUpdate.setCurrentPrice(updatedStockPrice.getCurrentPrice());
        stockDtoOutAfterUpdate.setLastUpdate(now);

        when(stockService.getById(stockId)).thenReturn(stockDtoOutBeforeUpdate);
        when(stockService.updatePrice(any(Long.class), any(StockPriceDtoEditIn.class))).thenReturn(stockDtoOutAfterUpdate);

        ResultActions response = mockMvc.perform(patch("/stocks/{id}", stockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStockPrice)));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value(stockDtoOutAfterUpdate.getName()))
                .andExpect(jsonPath("$.lastUpdate").value(stockDtoOutAfterUpdate.getLastUpdate().toString()))
                .andExpect(jsonPath("$.currentPrice").value(stockDtoOutAfterUpdate.getCurrentPrice()));
    }

    @Test
    public void givenStockId_whenDeleteStock_thenReturnBoolean() throws Exception {
        long stockId = 1L;
        when(stockService.deleteById(any(Long.class))).thenReturn(Boolean.TRUE);
        ResultActions response = mockMvc.perform(delete("/stocks/{id}", stockId));
        response.andExpect(status().isOk())
                .andDo(print());
    }
}