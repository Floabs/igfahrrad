package at.igfahrrad;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BicycleJsonRepository {

    public static final Path FILEPATH = Path.of("bikes.json");

    public Bicycle[] load() throws IOException {
        var json = Files.readString(FILEPATH);
        var mapper = new ObjectMapper();
        return mapper.readValue(json, Bicycle[].class);
    }

    public List<Bicycle> add(Bicycle bicycle) throws IOException {
        var bicycles = List.of(this.load());
        var updatedBicycles = new LinkedList<>(bicycles);
        updatedBicycles.add(bicycle);
        updateBicyclesJson(updatedBicycles);
        return updatedBicycles;
    }

    public List<Bicycle> deleteBicycle(Long id) throws IOException {
        var currentBicycles = this.load();
        var bicycles = List.of(currentBicycles)
            .stream()
            .filter(bike -> !bike.id.equals(id))
            .toList();
        updateBicyclesJson(bicycles);
        return bicycles;
    }

    public void updateBicyclesJson(List<Bicycle> bicycles) throws IOException {
        var mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(bicycles);
        Files.writeString(FILEPATH, json);
    }
}
