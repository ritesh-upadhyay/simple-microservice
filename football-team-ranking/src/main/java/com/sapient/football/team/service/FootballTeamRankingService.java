package com.sapient.football.team.service;

import java.util.Optional;

import com.sapient.football.team.response.FootballTeamRankingResponse;

public interface FootballTeamRankingService {

	public Optional<FootballTeamRankingResponse> getRanking(String country, String league, String team);

}
