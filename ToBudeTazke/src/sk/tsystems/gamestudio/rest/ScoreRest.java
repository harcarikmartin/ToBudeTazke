package sk.tsystems.gamestudio.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import sk.tsystems.gamestudio.entity.jpa.Score;
import sk.tsystems.gamestudio.service.jpa.ScoreJpa;

@Path("/scores")
public class ScoreRest {
	private static List<Score> scores;
	
	static {
		scores = new ScoreJpa().findScores();
	}

	@GET
	@Produces("application/json")
	public List<Score> getScores() {
		return scores;
	}

	@GET
	@Path("/{index}")
	@Produces("application/json")
	public Score getScore(@PathParam("index") int index) {
		return scores.get(index - 1);
	}
	
	@POST
	@Consumes("application/json")
	public Response addScore(Score score) {
		scores.add(score);
		return Response.ok().build();
	}
	
	
}
