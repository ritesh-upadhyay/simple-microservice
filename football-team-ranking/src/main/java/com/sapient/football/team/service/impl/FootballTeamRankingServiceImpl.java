package com.sapient.football.team.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.football.team.common.FootballTeamRankingConstants;
import com.sapient.football.team.common.FootballTeamRankingHttpClent;
import com.sapient.football.team.exception.FootballTeamRankingInternalServerError;
import com.sapient.football.team.exception.FootballTeamRankingNotFoundException;
import com.sapient.football.team.model.Competitions;
import com.sapient.football.team.model.Country;
import com.sapient.football.team.model.Standings;
import com.sapient.football.team.response.FootballTeamRankingResponse;
import com.sapient.football.team.service.FootballTeamRankingService;

@Service
public class FootballTeamRankingServiceImpl implements FootballTeamRankingService {

	private static final String COUNTRY_ID_NOT_FOUND = "country id not found";

	private static final String LEAGUE_ID_NOT_FOUND = "league id not found";

	private static final String LEAGUE_NAME_NOT_FOUND = "league name not found";

	private static final String COUNTRY_NAME_NOT_FOUND = "country name not found";

	private static final String TEAM_NAME_NOT_FOUND = "team name not found";

	private static Logger logger = LoggerFactory.getLogger(FootballTeamRankingServiceImpl.class);

	@Autowired
	private FootballTeamRankingHttpClent httpClient;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${football.api.domain.name}")
	private String footballApiDomainName;

	@Value("${football.api.key}")
	private String footballApiKey;

	@Override
	public Optional<FootballTeamRankingResponse> getRanking(String country, String league, String team) {
		try {
			String countryUrl = footballApiDomainName + FootballTeamRankingConstants.ACTION_GET_COUNTRY + "&"
					+ FootballTeamRankingConstants.API_KEY + footballApiKey;
			logger.debug("country url:{}", countryUrl);
			List<Country> countryResponse = objectMapper.readValue(httpClient.doGet(countryUrl),
					new TypeReference<List<Country>>() {
					});
			Optional<String> countryId = getCountryId(countryResponse, country);
			String leagueUrl = footballApiDomainName + FootballTeamRankingConstants.ACTION_GET_LEAGUES_WITH_COUNTRY_ID
					+ countryId.get() + "&" + FootballTeamRankingConstants.API_KEY + footballApiKey;
			logger.debug("league url:{}", leagueUrl);
			List<Competitions> leagueResponse = objectMapper.readValue(httpClient.doGet(leagueUrl),
					new TypeReference<List<Competitions>>() {
					});
			Optional<String> leagueId = getLeagueId(leagueResponse, league);
			String standingsUrl = footballApiDomainName
					+ FootballTeamRankingConstants.ACTION_GET_STANDINGS_WITH_LEAGUE_ID + leagueId.get() + "&"
					+ FootballTeamRankingConstants.API_KEY + footballApiKey;
			logger.debug("standings url:{}", standingsUrl);
			List<Standings> standingsResponse = objectMapper.readValue(httpClient.doGet(standingsUrl),
					new TypeReference<List<Standings>>() {
					});
			return getfootballTeamRankingResponse(standingsResponse, countryId.get(), team);
		} catch (FootballTeamRankingNotFoundException e) {
			throw e;
		} catch (JsonProcessingException e) {
			logger.error("Exception while parsing the response. JsonMappingException. error_message={}",
					e.getMessage());
			throw new FootballTeamRankingInternalServerError("Exception while parsing the response");
		} catch (Exception e) {
			logger.error(
					"Exception occurred while processing the ranking. country={}; league={}; team={}; errorMessage={}",
					country, league, team, e.getMessage());
			throw new FootballTeamRankingInternalServerError("Exception while getting the ranking");
		}
	}

	private Optional<String> getCountryId(List<Country> countryResponse, String countryName) {
		if (countryResponse == null || countryResponse.isEmpty())
			throw new FootballTeamRankingInternalServerError(COUNTRY_ID_NOT_FOUND);
		for (Country country : countryResponse) {
			if (country.getName().equalsIgnoreCase(countryName))
				return Optional.of(country.getId());
		}
		throw new FootballTeamRankingNotFoundException(COUNTRY_NAME_NOT_FOUND);
	}

	private Optional<FootballTeamRankingResponse> getfootballTeamRankingResponse(List<Standings> standingsResponse,
			String countryId, String teamName) {
		for (Standings standing : standingsResponse) {
			if (standing.getTeamName().equalsIgnoreCase(teamName))
				return getfootballTeamRankingResponseSchema(standing, countryId);
		}
		throw new FootballTeamRankingNotFoundException(TEAM_NAME_NOT_FOUND);
	}

	private Optional<FootballTeamRankingResponse> getfootballTeamRankingResponseSchema(Standings standing,
			String countryId) {
		FootballTeamRankingResponse response = new FootballTeamRankingResponse();
		response.setCountryId(countryId);
		response.setCountryName(standing.getCountryName());
		response.setLeagueId(standing.getLeagueId());
		response.setLeagueName(standing.getLeagueName());
		response.setTeamId(standing.getTeamId());
		response.setTeamName(standing.getTeamName());
		response.setOverallLeaguePosition(standing.getOverallLeaguePosition());
		return Optional.of(response);
	}

	private Optional<String> getLeagueId(List<Competitions> leagueResponse, String league) {
		if (leagueResponse == null || leagueResponse.isEmpty())
			throw new FootballTeamRankingInternalServerError(LEAGUE_ID_NOT_FOUND);
		for (Competitions competition : leagueResponse) {
			if (competition.getLeagueName().equalsIgnoreCase(league))
				return Optional.of(competition.getLeagueId());
		}
		throw new FootballTeamRankingNotFoundException(LEAGUE_NAME_NOT_FOUND);
	}

}
