package com.sapient.football.team.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sapient.football.team.exception.FootballTeamRankingBadRequestException;
import com.sapient.football.team.exception.FootballTeamRankingInternalServerError;
import com.sapient.football.team.exception.FootballTeamRankingNotFoundException;

@RestControllerAdvice
public class GenericExceptionAdvice {

	@ExceptionHandler(FootballTeamRankingBadRequestException.class)
	ResponseEntity<String> invalidPayload(FootballTeamRankingBadRequestException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(FootballTeamRankingInternalServerError.class)
	ResponseEntity<String> internalServerError(FootballTeamRankingInternalServerError e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
	
	@ExceptionHandler(FootballTeamRankingNotFoundException.class)
	ResponseEntity<String> notFound(FootballTeamRankingNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    ResponseEntity<String> methodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
    }

}
