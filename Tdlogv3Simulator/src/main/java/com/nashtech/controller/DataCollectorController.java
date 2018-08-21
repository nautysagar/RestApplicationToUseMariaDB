package com.nashtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nashtech.business.TdlogSimulator;
import com.nashtech.dataCollector.pojo.TdlogResult;

@Controller
public class DataCollectorController {

	@Autowired
	TdlogSimulator sim;

	@RequestMapping(value = "/create", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> create(int td, int tdc) {

		if (td < 1 || tdc < 1) {
			td = 1;
			tdc = 10;
		}
		TdlogResult result = sim.create(td, tdc);

		return getStatus(result);

	}

	@RequestMapping(value = "/vivek", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> create() {
		return new ResponseEntity<String>("Hi Vivek", HttpStatus.OK);

	}

	private ResponseEntity<String> getStatus(TdlogResult result) {

		if (result.getErrorLevel() == 0) {
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(result.toString(), HttpStatus.EXPECTATION_FAILED);
		}

	}

}
