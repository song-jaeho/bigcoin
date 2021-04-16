package com.bigcoin.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CurrencyData implements Serializable {

	
	/*
	private int opening_price;
	private int closing_price;
	private int min_price;
	private int max_price;
	private long units_traded;
	private long acc_trade_value;
	private int prev_closing_price;
	private long units_traded_24H;
	private long acc_trade_value_24H;
	private int fluctate_24H;
	private int fluctate_rate_24H;
	private int date;
	*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// BithumbData에서 entry로 관리하기 때문에 쓸지 안 쓸지...
	@JsonProperty("ticker")
	private String ticker;
	
	@JsonProperty("opening_price")
	private String openingPrice;
	
	@JsonProperty("closing_price")
	private String closingPrice;
	
	@JsonProperty("min_price")
	private String minPrice;
	
	@JsonProperty("max_price")
	private String maxPrice;
	
	@JsonProperty("units_traded")
	private String unitsTraded;
	
	@JsonProperty("acc_trade_value")
	private String accTradeValue;
	
	@JsonProperty("prev_closing_price")
	private String prevClosingPrice;
	
	@JsonProperty("units_traded_24H")
	private String unitsTraded24H;
	
	@JsonProperty("acc_trade_value_24H")
	private String accTradeValue24H;
	
	@JsonProperty("fluctate_24H")
	private String fluctate24H;
	
	@JsonProperty("fluctate_rate_24H")
	private String fluctateRate24H;

	
}
