package com.sapient.football.team.service.impl;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.football.team.common.FootballTeamRankingHttpClent;
import com.sapient.football.team.exception.FootballTeamRankingBadRequestException;
import com.sapient.football.team.exception.FootballTeamRankingInternalServerError;
import com.sapient.football.team.exception.FootballTeamRankingNotFoundException;
import com.sapient.football.team.model.Competitions;
import com.sapient.football.team.model.Country;
import com.sapient.football.team.model.Standings;
import com.sapient.football.team.response.FootballTeamRankingResponse;

@WebMvcTest
public class FootballTeamRankingServiceImplTest {

	@Mock
	private FootballTeamRankingHttpClent httpClient;
	
	@Mock
	private ObjectMapper objectMapper;
	
	@InjectMocks
	FootballTeamRankingServiceImpl service = new FootballTeamRankingServiceImpl();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getRanking() throws JsonMappingException, JsonProcessingException {
		when(httpClient.doGet(Mockito.anyString())).thenReturn("string");
		when(objectMapper.readValue(Mockito.anyString(), Mockito.any(TypeReference.class))).thenReturn(getListOfCountry())
		.thenReturn(getListOfCompetitions())
		.thenReturn(getListOfStandings());
		Optional<FootballTeamRankingResponse> res = service.getRanking("cname", "lname", "tname");
		assertSame(res.get().getCountryId(), "cid");
		assertSame(res.get().getCountryName(), "cname");
		assertSame(res.get().getLeagueId(), "lid");
		assertSame(res.get().getLeagueName(), "lname");
		assertSame(res.get().getTeamId(), "tid");
		assertSame(res.get().getTeamName(), "tname");
		assertSame(res.get().getOverallLeaguePosition(), "overallLeaguePosition");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getRanking_CountryNotFound() throws JsonMappingException, JsonProcessingException {
		when(httpClient.doGet(Mockito.anyString())).thenReturn("string");
		when(objectMapper.readValue(Mockito.anyString(), Mockito.any(TypeReference.class))).thenReturn(getListOfCountry());
		Assertions.assertThrows(FootballTeamRankingNotFoundException.class, () -> {
			service.getRanking("cnames", "lname", "tname");
		  });
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getRanking_NoCountryResultFromGetCall() throws JsonMappingException, JsonProcessingException {
		when(httpClient.doGet(Mockito.anyString())).thenReturn("string");
		when(objectMapper.readValue(Mockito.anyString(), Mockito.any(TypeReference.class))).thenReturn(null);
		Assertions.assertThrows(FootballTeamRankingInternalServerError.class, () -> {
			service.getRanking("cnames", "lname", "tname");
		  });
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getRanking_LeagueNotFound() throws JsonMappingException, JsonProcessingException {
		when(httpClient.doGet(Mockito.anyString())).thenReturn("string");
		when(objectMapper.readValue(Mockito.anyString(), Mockito.any(TypeReference.class))).thenReturn(getListOfCountry())
		.thenReturn(getListOfCompetitions());
		Assertions.assertThrows(FootballTeamRankingNotFoundException.class, () -> {
			service.getRanking("cname", "lnames", "tname");
		  });
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getRanking_NoLeagueResultFromGetCall() throws JsonMappingException, JsonProcessingException {
		when(httpClient.doGet(Mockito.anyString())).thenReturn("string");
		when(objectMapper.readValue(Mockito.anyString(), Mockito.any(TypeReference.class))).thenReturn(getListOfCountry()).thenReturn(null);
		Assertions.assertThrows(FootballTeamRankingInternalServerError.class, () -> {
			service.getRanking("cname", "lname", "tname");
		  });
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getRanking_TeamNotFound() throws JsonMappingException, JsonProcessingException {
		when(httpClient.doGet(Mockito.anyString())).thenReturn("string");
		when(objectMapper.readValue(Mockito.anyString(), Mockito.any(TypeReference.class))).thenReturn(getListOfCountry()).thenReturn(getListOfCompetitions())
		.thenReturn(getListOfStandings());
		Assertions.assertThrows(FootballTeamRankingNotFoundException.class, () -> {
			service.getRanking("cname", "lname", "tnames");
		  });
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void getRankingJsonMappingException() throws JsonMappingException, JsonProcessingException {
		when(httpClient.doGet(Mockito.anyString())).thenReturn("string");
		when(objectMapper.readValue(Mockito.anyString(), Mockito.any(TypeReference.class))).thenThrow(new JsonMappingException("msg"));
		Assertions.assertThrows(FootballTeamRankingInternalServerError.class, () -> {
			service.getRanking("cname", "lname", "tname");
		  });
	}
	
	private List<Standings> getListOfStandings() {
		List<Standings> list = new ArrayList<>();
		Standings standings = new Standings();
		standings.setCountryName("cname");
		standings.setLeagueId("lid");
		standings.setLeagueName("lname");
		standings.setOverallLeaguePosition("overallLeaguePosition");
		standings.setTeamId("tid");
		standings.setTeamName("tname");
		list.add(standings);
		return list;
	}

	private List<Competitions> getListOfCompetitions() {
		List<Competitions> list = new ArrayList<>();
		Competitions comp = new Competitions();
		comp.setCountryId("cid");
		comp.setCountryName("cname");
		comp.setLeagueId("lid");
		comp.setLeagueName("lname");
		list.add(comp);
		return list;
	}

	private List<Country> getListOfCountry() {
		List<Country> list = new ArrayList<>();
		Country country = new Country();
		country.setId("cid");
		country.setName("cname");
		list.add(country);
		return list;
	}
}
