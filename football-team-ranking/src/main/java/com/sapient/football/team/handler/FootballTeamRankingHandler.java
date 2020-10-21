package com.sapient.football.team.handler;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.football.team.common.FootballTeamRankingConstants;
import com.sapient.football.team.exception.FootballTeamRankingBadRequestException;
import com.sapient.football.team.response.FootballTeamRankingResponse;
import com.sapient.football.team.service.FootballTeamRankingService;

@RestController
@RequestMapping(FootballTeamRankingConstants.FOOTBALL_TEAM_RANKING_PATH)
@Validated
public class FootballTeamRankingHandler {

	private static Logger logger = LoggerFactory.getLogger(FootballTeamRankingHandler.class);

	@Autowired
	FootballTeamRankingService service;

	@GetMapping(value = FootballTeamRankingConstants.GET_FOOTBALL_TEAM_RANKING_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FootballTeamRankingResponse> getFootballTeamRanking(
			@RequestParam(required = false, name = FootballTeamRankingConstants.COUNTRY) String country,
			@RequestParam(required = false, name = FootballTeamRankingConstants.LEAGUE) String league,
			@RequestParam(required = false, name = FootballTeamRankingConstants.TEAM) String team) {
		try {
			validateQueryParam(country, FootballTeamRankingConstants.COUNTRY);
			validateQueryParam(league, FootballTeamRankingConstants.LEAGUE);
			validateQueryParam(team, FootballTeamRankingConstants.TEAM);
			logger.info(
					"api=getFootballTeamRanking; method=GET; executionType=SyncFlow; executionState=STARTED; status=PROCESSING");
			Optional<FootballTeamRankingResponse> responseBody = service.getRanking(country, league, team);
			if (responseBody.isPresent()) {
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responseBody.get());
			} else {
				logger.info(
						"api=getFootballTeamRanking; method=GET; executionType=SyncFlow; executionState=COMPLETED; status=SUCCESS; httpStatusCode=404");
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			logger.error(
					"api=getFootballTeamRanking; method=GET; executionType=SyncFlow; executionState=COMPLETED; status=FAILED");
			throw e;
		}
	}

	private void validateQueryParam(String queryParam, String queryParamName) {
		if (StringUtils.isBlank(queryParam)) {
			throw new FootballTeamRankingBadRequestException("invalid value for " + queryParamName);
		}
	}
}
