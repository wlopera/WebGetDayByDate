package com.wlopera.spring.controller;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Wrapper")
public class Wrapper implements Serializable {
	
	private static final long serialVersionUID = -2156793465167257122L;

	private String nameDay;

	public String getNameDay() {
		return nameDay;
	}

	public void setNameDay(String nameDay) {
		this.nameDay = nameDay;
	}

}
