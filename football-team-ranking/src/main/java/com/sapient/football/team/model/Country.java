package com.sapient.football.team.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

	@JsonProperty("id")
	@JsonAlias("country_id")
	private String id;
	
	@JsonProperty("name")
	@JsonAlias("country_name")
	private String name;
}
