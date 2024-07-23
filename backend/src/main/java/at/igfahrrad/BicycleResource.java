package at.igfahrrad;

import java.io.IOException;
import java.util.List;

import org.jboss.logging.Logger;

import com.arjuna.ats.internal.arjuna.objectstore.jdbc.drivers.mariadb_driver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
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
        var bycicles = bicycleRepository.load();
        return List.of(bycicles);
    }
    @GET
    @Path("/kurt")
    public Bicycle helloKurt() {
        var greeting = new Bicycle();
        greeting.name = "hallo kurt";
        return greeting;
    }
    @POST
    public Response store(Bicycle bicycle) throws JsonProcessingException{
        var mapper = new ObjectMapper();
        var jason = mapper.writeValueAsString(bicycle);
        log.info(jason);
        var uri = UriBuilder
            .fromResource(BicycleResource.class)
            .path("/7")
            .build();
        return Response.ok().location(uri).build();
    }
    @GET
    @Path("/{id}")
    public Bicycle getbBicycle(@PathParam("id") String id){
        log.infof("get Bicycle with id %s", id);
        return new Bicycle();
    }
    @POST
    @Path("/add")
    public Response addBicycle(Bicycle bicycle) throws IOException {
        var newBicyles = bicycleRepository.add(bicycle);
        bicycleRepository.updateBicyclesJson(newBicyles);
        var mapper = new ObjectMapper();
        var jason = mapper.writeValueAsString(newBicyles);
        log.info(jason);
        var uri = UriBuilder
            .fromResource(BicycleResource.class)
            .path("/7")
            .build();
        return Response.ok().location(uri).build();
    }

    @POST
    @Path("/delete")
    public Response deleteBicycle(Bicycle bicycle) throws IOException {
        var newBicyles = bicycleRepository.deleteBicycle(bicycle);
        bicycleRepository.updateBicyclesJson(newBicyles);
        var mapper = new ObjectMapper();
        var jason = mapper.writeValueAsString(newBicyles);
        log.info(jason);
        var uri = UriBuilder
            .fromResource(BicycleResource.class)
            .path("/7")
            .build();
        return Response.ok().location(uri).build();
    }

    
}