package com.bigcoin.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BithumbData {

	@JsonProperty("data")
	//private Entry<String, CurrencyData> data;
	private List<CurrencyData> data = new ArrayList<CurrencyData>();
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("date")
	private String date;
}
