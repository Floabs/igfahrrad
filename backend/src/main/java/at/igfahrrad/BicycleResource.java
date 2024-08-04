package at.igfahrrad;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("/bicycles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BicycleResource {
    @Inject
    BicycleRepository bicycleRepository;

    @GET
    public List<Bicycle> getAll() {
        return bicycleRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Bicycle getBicycle(@PathParam("id") Long id) {
        return bicycleRepository.findById(id);
    }

    @POST
    @Transactional
    @Path("/save")
    public Response addBicycle(Bicycle bicycle) {
        bicycleRepository.persist(bicycle);
        return Response.ok(bicycle).status(Response.Status.CREATED).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addBicycle(@FormParam("name") String name,
                               @FormParam("description") String description,
                               @FormParam("price") BigDecimal price,
                               @FormParam("type") String type,
                               @FormParam("image") String image) throws MalformedURLException, URISyntaxException {
        Bicycle bicycle = new Bicycle();
        bicycle.name = name;
        bicycle.description = description;
        bicycle.price = price;
        bicycle.type = type;
        bicycle.image = image;
        bicycleRepository.persist(bicycle);
        var uri = UriBuilder
            .fromResource(AdminResource.class)
            .build();
        return Response.seeOther(uri).build();
    }

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response deleteBicycle(@FormParam("id") Long id) {
        boolean deleted = bicycleRepository.deleteById(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}


  /*  
    @POST
    @Transactional
    @Path("/save")
    public Response addBicycle(Bicycle bicycle) {
        bicycleRepository.persist(bicycle);
        return Response.ok(bicycle).status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteBicycle(@PathParam("id") Long id) {
        boolean deleted = bicycleRepository.deleteById(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
    */
