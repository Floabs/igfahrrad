package at.igfahrrad;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    UserService userService;

    @POST
    @Transactional
    public Response login(UserCredentials credentials) {
        if (userService.validateUser(credentials.getUsername(), credentials.getPassword())) {
            String token = Jwt.issuer("http://localhost:8080")
                             .upn(credentials.getUsername())
                             .groups("admin")
                             .sign();
            return Response.ok(new TokenResponse(token)).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}