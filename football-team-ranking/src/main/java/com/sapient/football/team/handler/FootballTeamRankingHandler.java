package com.sapient.football.team.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.football.team.common.FootballTeamRankingConstants;
import com.sapient.football.team.service.FootballTeamRankingService;

@RestController
@RequestMapping(FootballTeamRankingConstants.FOOTBALL_TEAM_RANKING_PATH)
public class FootballTeamRankingHandler {

	private static Logger logger = LoggerFactory.getLogger(FootballTeamRankingHandler.class);
	
	@Autowired
	FootballTeamRankingService service;
	
	@GetMapping(value = FootballTeamRankingConstants.GET_FOOTBALL_TEAM_RANKING_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getFootballTeamRanking(@RequestParam(name = FootballTeamRankingConstants.COUNTRY) String country, @RequestParam( name = FootballTeamRankingConstants.LEAGUE) String league,
			 @RequestParam(name = FootballTeamRankingConstants.TEAM) String team) {
		try {
			logger.info("api=getFootballTeamRanking; method=GET; executionType=SyncFlow; executionState=STARTED; status=PROCESSING");
			service.getRanking();
			return null;
		} catch(Exception e) {
			logger.info("api=getFootballTeamRanking; method=GET; executionType=SyncFlow; executionState=COMPLETED; status=FAILED");
			throw e;
		}
		
	}
}
