package com.bigcoin.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SampleData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String coinName;
	private String body;
	
}
