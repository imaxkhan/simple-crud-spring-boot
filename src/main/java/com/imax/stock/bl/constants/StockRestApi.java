package com.imax.stock.bl.constants;

import lombok.Getter;

@Getter
public abstract class StockRestApi {
    public static final String STOCKS = "/stocks";
    public static final String STOCKS_ID = "/stocks/{id}";
    public static final String STOCKS_COUNT = "/stocks/count";
}