package edu.my.service.logic_layer.impl;

import edu.my.data.entity.MovieEntity;
import edu.my.data.repository.MovieRepository;
import edu.my.service.logic_layer.MassiveMovieProcessingService;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ApplicationScoped
@IfBuildProfile("teststand")
public class MassiveMovieProcessingServiceImpl implements MassiveMovieProcessingService {
    @Inject
    MovieRepository movieRepository;

    @Override
    public void add(Integer count) {
        for (int i = 0; i < count; i++) {
            MovieEntity movieEntity = new MovieEntity();
            movieEntity.name = "TestName_" + i;
            movieEntity.description = "TestDescription_" + i;
            movieEntity.reasonsToView = "TestReasonsToView_" + i;
            movieEntity.facts = "TestFacts_" + i;
            movieEntity.durationInSeconds = ThreadLocalRandom.current().nextInt(60, 21601);
            movieEntity.distributor = "TestDistributor_" + i;
            movieEntity.country = "TestCountry_" + i;
            movieEntity.releaseYear = ThreadLocalRandom.current().nextInt(1895, 2101);
            movieRepository.persist(movieEntity);
        }
    }

    @Override
    public void updateAllFacts() {
        List<MovieEntity> movieEntityList = movieRepository.findAll().list();
        for (MovieEntity entity : movieEntityList) {
            if (entity.facts != null) {
                if (entity.facts.length() < 256)
                    entity.facts += "\n- Blah-blah-blah. It is an additional fact.";
            } else {
                entity.facts = "Not stated.";
            }
        }
    }
}
