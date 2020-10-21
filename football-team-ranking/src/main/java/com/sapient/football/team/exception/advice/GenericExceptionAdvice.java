package com.sapient.football.team.exception.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sapient.football.team.exception.FootballTeamRankingBadRequestException;

@RestControllerAdvice
public class GenericExceptionAdvice {
	
	@ExceptionHandler(FootballTeamRankingBadRequestException.class)
	ResponseEntity<String> invalidPayload(FootballTeamRankingBadRequestException e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
