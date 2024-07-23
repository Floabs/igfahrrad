package at.igfahrrad;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BicycleRepository {
    
    public static final Path FILEPATH = Path.of("bikes.json"); 

    public Bicycle[] load() throws IOException {
        var jason = Files.readString(FILEPATH);
        var mapper = new ObjectMapper();
        var bicycles = mapper.readValue(jason, Bicycle[].class);
        return bicycles;
    }
    public List<Bicycle> add (Bicycle bicycle) throws IOException {
        var bicycles = List.of(this.load());
        var updatedbicycles = new LinkedList<>(bicycles);
        updatedbicycles.add(bicycle);

        return updatedbicycles;
    }

    public List<Bicycle> deleteBicycle(Bicycle bicycle) throws IOException {
        var currentBicycles = this.load();
        var biycles = List.of(currentBicycles)
            .stream()
            .filter(bike -> !bike.name.equals(bicycle.name))
            .toList();
        return biycles;
    }
    public void updateBicyclesJson(List<Bicycle> bicycles) throws IOException {
        var mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(bicycles);
        Files.writeString(FILEPATH, json);
    }
}
