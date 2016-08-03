package sk.tsystems.gamestudio.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import sk.tsystems.gamestudio.entity.jpa.Comment;
import sk.tsystems.gamestudio.service.jpa.CommentJpa;

@Path("/comments")
public class CommentRest {
private static List<Comment> comments;
	
	static {
		comments = new CommentJpa().findComments();
	}

	@GET
	@Produces("application/json")
	public List<Comment> getComments() {
		return comments;
	}

	@GET
	@Path("/{index}")
	@Produces("application/json")
	public Comment getComment(@PathParam("index") int index) {
		return comments.get(index - 1);
	}
	
	@POST
	@Consumes("application/json")
	public Response addComment(Comment comment) {
		comments.add(comment);
		return Response.ok().build();
	}
	
	
}
