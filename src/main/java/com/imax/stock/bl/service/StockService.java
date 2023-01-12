package com.imax.stock.bl.service;

import com.imax.stock.bl.domain.dto.PageableFilter;
import com.imax.stock.bl.domain.dto.StockDtoIn;
import com.imax.stock.bl.domain.dto.StockDtoOut;
import com.imax.stock.bl.domain.dto.StockPriceDtoEditIn;
import com.imax.stock.bl.domain.model.StockModel;
import com.imax.stock.exception.CustomException;
import com.imax.stock.exception.SystemError;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {
    private final StockRepository stockRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StockService(StockRepository stockRepository, ModelMapper modelMapper) {
        this.stockRepository = stockRepository;
        this.modelMapper = modelMapper;
    }

    public List<StockDtoOut> getAll(PageableFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        Page<StockModel> stockModelBucket = stockRepository.findAll(pageable);
        return stockModelBucket.get().map(StockDtoOut::new).collect(Collectors.toList());
    }

    public StockDtoOut getById(long id) throws CustomException {
        StockModel stockModel = checkExistence(id);
        return new StockDtoOut(stockModel);

    }

    public StockDtoOut create(StockDtoIn dto) {
        StockModel stockModel = modelMapper.map(dto, StockModel.class);
        stockRepository.save(stockModel);
        return new StockDtoOut(stockModel);
    }

    public StockDtoOut updatePrice(long id, StockPriceDtoEditIn priceDtoEditDtoIn) throws CustomException {
        StockModel existingStockModel = checkExistence(id);
        modelMapper.map(priceDtoEditDtoIn, existingStockModel);
        stockRepository.save(existingStockModel);
        return new StockDtoOut(existingStockModel);
    }

    public boolean deleteById(long id) throws CustomException {
        checkExistence(id);
        stockRepository.deleteById(id);
        return true;
    }

    private StockModel checkExistence(long id) throws CustomException {
        Optional<StockModel> existingStockModel = stockRepository.findById(id);
        if (existingStockModel.isPresent()) {
            return existingStockModel.get();
        } else {
            throw new CustomException(SystemError.DATA_NOT_FOUND, "Stock With this id not found", 10027);
        }
    }
}
