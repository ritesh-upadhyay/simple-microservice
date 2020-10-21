package com.sapient.football.team.handler;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sapient.football.team.common.FootballTeamRankingConstants;
import com.sapient.football.team.exception.FootballTeamRankingBadRequestException;
import com.sapient.football.team.exception.advice.GenericExceptionAdvice;
import com.sapient.football.team.response.FootballTeamRankingResponse;
import com.sapient.football.team.service.FootballTeamRankingService;

@WebMvcTest(FootballTeamRankingHandler.class)
public class FootballTeamRankingHandlerTest {

	@Mock
	private FootballTeamRankingService service;
	
	@InjectMocks
	private FootballTeamRankingHandler handler = new FootballTeamRankingHandler();
	
	@Autowired
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(handler).setControllerAdvice(GenericExceptionAdvice.class).build();
	}
	
	@Test
	public void testGetRanking() throws Exception {
		when(service.getRanking(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getFootballTeamRankingResponse());
		ResponseEntity<FootballTeamRankingResponse> res = handler.getFootballTeamRanking("country", "league", "team");
		assertSame(HttpStatus.OK, res.getStatusCode());
	}
	
	@Test
	public void testGetRankingNotFound() throws Exception {
		when(service.getRanking(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());
		ResponseEntity<FootballTeamRankingResponse> res = handler.getFootballTeamRanking("country", "league", "team");
		assertSame(HttpStatus.NOT_FOUND, res.getStatusCode());
	}
	
	@Test
	public void testGetRanking_FootballTeamRankingBadRequestException() throws Exception {
		Assertions.assertThrows(FootballTeamRankingBadRequestException.class, () -> {
			handler.getFootballTeamRanking(null, "league", "team");
		  });
	}
	
	

	private Optional<FootballTeamRankingResponse> getFootballTeamRankingResponse() {
		FootballTeamRankingResponse res = new FootballTeamRankingResponse();
		res.setCountryId("countryId");
		res.setCountryName("countryName");
		res.setLeagueId("leagueId");
		res.setLeagueName("leagueName");
		res.setOverallLeaguePosition("overallLeaguePosition");
		res.setTeamId("teamId");
		res.setTeamName("teamName");
		return Optional.of(res);
	}
	
	@Test
	public void testGetRankingBadRequest() throws Exception {
		when(service.getRanking(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getFootballTeamRankingResponse());
		mockMvc.perform(MockMvcRequestBuilders
				.get(FootballTeamRankingConstants.FOOTBALL_TEAM_RANKING_PATH + FootballTeamRankingConstants.GET_FOOTBALL_TEAM_RANKING_URI)).andExpect(status().isBadRequest());
	}
	
}
