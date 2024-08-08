package at.igfahrrad;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/admin")
public class AdminResource {

    @Inject
    Template admin;

    @Inject
    BicycleRepository bicycleRepository;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("user")
    public TemplateInstance get() {
        List<Bicycle> bikes = bicycleRepository.listAll();
        return admin.data("bikes", bikes);
    }
}
