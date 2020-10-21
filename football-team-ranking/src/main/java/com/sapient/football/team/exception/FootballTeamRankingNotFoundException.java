package com.sapient.football.team.exception;

public class FootballTeamRankingNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -8241608132634994540L;

	public FootballTeamRankingNotFoundException(String msg) {
		super(msg);
	}
}
