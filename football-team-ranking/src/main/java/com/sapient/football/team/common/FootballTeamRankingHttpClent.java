package com.sapient.football.team.common;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sapient.football.team.exception.FootballTeamRankingBadRequestException;
import com.sapient.football.team.exception.FootballTeamRankingInternalServerError;

@Component
public class FootballTeamRankingHttpClent {

	private static Logger logger = LoggerFactory.getLogger(FootballTeamRankingHttpClent.class);

	@Autowired
	private CloseableHttpClient closeableHttpClient;

	public String doGet(String uri) {
		HttpGet request = new HttpGet(uri);
		try (CloseableHttpResponse response = closeableHttpClient.execute(request)) {
			String responseBody = EntityUtils.toString(response.getEntity());
			if (response.getStatusLine().getStatusCode() == 200) {
				return responseBody;
			} else if (response.getStatusLine().getStatusCode() >= 400
					&& response.getStatusLine().getStatusCode() <= 499) {
				logger.error("4XX returned from the footballapi. uri={}; responseBody={}", uri, responseBody);
				throw new FootballTeamRankingBadRequestException("4XX exception while calling the api");
			} else {
				logger.error("5XX returned from the footballapi. uri={}; responseBody={}", uri, responseBody);
				throw new FootballTeamRankingInternalServerError("5XX exception while calling the api");
			}
		} catch (FootballTeamRankingBadRequestException | FootballTeamRankingInternalServerError e) {
			throw e;
		} catch (Exception e) {
			logger.error("Exception occurred while calling the uri");
			throw new FootballTeamRankingInternalServerError("exception while doing get call");
		}
	}
}
