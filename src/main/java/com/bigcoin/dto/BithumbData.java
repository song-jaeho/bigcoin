package com.bigcoin.dto;

import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BithumbData {

	@JsonProperty("data")
	private Entry<String, CurrencyData> data;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("date")
	private String date;
	
}
