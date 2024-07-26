package at.igfahrrad;

import java.io.IOException;
import java.util.List;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.hibernate.orm.panache.Panache;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("/bicycles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BicycleResource {
    @Inject Logger log;
    @Inject BicycleRepository bicycleRepository;

    @GET
    public List<Bicycle> getAll() throws IOException {
        var bycicles = bicycleRepository.listAll();
        return bycicles;
    }
    @GET
    @Path("/kurt")
    public Bicycle helloKurt() {
        var greeting = new Bicycle();
        greeting.name = "hallo kurt";
        return greeting;
    }
    
    @GET
    @Path("/{id}")
    public Bicycle getbBicycle(@PathParam("id") Long id){
        return bicycleRepository.findById(id);
    }
    @POST
    @Transactional
    public Response addBicycle(Bicycle bicycle) throws IOException {
        bicycleRepository.persistAndFlush(bicycle);
        var uri = UriBuilder
            .fromResource(BicycleResource.class)
            .path(bicycle.id.toString())
            .build();
        return Response.ok().location(uri).build();        
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBicycle(@PathParam("id") Long id) throws IOException {
        bicycleRepository.deleteById(id);
        return Response.noContent().build();
    }

    
}