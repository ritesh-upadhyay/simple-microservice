package com.sapient.football.team.response;

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
public class FootballTeamRankingResponse {

	@JsonProperty("countryId")
	private String countryId;
	
	@JsonProperty("countryName")
	private String countryName;
	
	@JsonProperty("leagueId")
	private String leagueId;
	
	@JsonProperty("leagueName")
	private String leagueName;
	
	@JsonProperty("teamId")
	private String teamId;
	
	@JsonProperty("teamName")
	private String teamName;
	
	@JsonProperty("overallLeaguePosition")
	private String overallLeaguePosition;
}
