package com.sapient.football.team.exception;

public class FootballTeamRankingBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 5395214036297941328L;

	public FootballTeamRankingBadRequestException(String msg) {
		super(msg);
	}
}
