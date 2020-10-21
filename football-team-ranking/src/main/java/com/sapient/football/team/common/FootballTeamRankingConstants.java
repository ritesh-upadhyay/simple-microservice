package com.sapient.football.team.common;

public class FootballTeamRankingConstants {
	
	private FootballTeamRankingConstants() {}
	
	public static final String FOOTBALL_TEAM_RANKING_PATH = "/footballteamranking/v1";
	public static final String GET_FOOTBALL_TEAM_RANKING_URI = "/ranking";
	public static final String BASE_PACKAGE = "com.sapient.football.team";
	public static final String COUNTRY = "country";
	public static final String LEAGUE = "league";
	public static final String TEAM = "team";
	public static final String ACTION_GET_COUNTRY = "?action=get_countries";
	public static final String ACTION_GET_LEAGUES_WITH_COUNTRY_ID = "?action=get_leagues&country_id=";
	public static final String ACTION_GET_STANDINGS_WITH_LEAGUE_ID = "?action=get_standings&league_id=";
	public static final String API_KEY = "APIkey=";
}
