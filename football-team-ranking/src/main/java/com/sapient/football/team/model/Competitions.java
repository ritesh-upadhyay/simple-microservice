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
public class Competitions {
	
	@JsonProperty("countryId")
	@JsonAlias("country_id")
	private String countryId;
	
	@JsonProperty("countryName")
	@JsonAlias("country_name")
	private String countryName;
	
	@JsonProperty("leagueId")
	@JsonAlias("league_id")
	private String leagueId;
	
	@JsonProperty("leagueName")
	@JsonAlias("league_name")
	private String leagueName;
}
