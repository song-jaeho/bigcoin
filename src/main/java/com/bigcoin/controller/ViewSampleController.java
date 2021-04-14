package com.bigcoin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/"})
public class ViewSampleController {

	@RequestMapping(value = {"/sample"})
	public String sampleView() {
		return "sample.html";
	}
}
