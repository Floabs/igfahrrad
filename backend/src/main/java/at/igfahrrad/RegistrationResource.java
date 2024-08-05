package at.igfahrrad;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationResource {

    @Inject
    UserService userService;

    @POST
    @Transactional
    public Response register(UserCredentials credentials) {
        userService.registerUser(credentials.getUsername(), credentials.getPassword());
        return Response.status(Response.Status.CREATED).build();
    }
}

