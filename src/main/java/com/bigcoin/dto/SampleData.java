package com.bigcoin.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SampleData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status;
	
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
	
}
