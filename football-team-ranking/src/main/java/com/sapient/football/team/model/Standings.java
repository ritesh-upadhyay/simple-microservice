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
public class Standings {
	
	@JsonProperty("countryName")
	@JsonAlias("country_name")
	private String countryName;
	
	@JsonProperty("leagueId")
	@JsonAlias("league_id")
	private String leagueId;
	
	@JsonProperty("leagueName")
	@JsonAlias("league_name")
	private String leagueName;
	
	@JsonProperty("teamId")
	@JsonAlias("team_id")
	private String teamId;
	
	@JsonProperty("teamName")
	@JsonAlias("team_name")
	private String teamName;
	
	
	@JsonProperty("overallLeaguePosition")
	@JsonAlias("overall_league_position")
	private String overallLeaguePosition;
	
}
