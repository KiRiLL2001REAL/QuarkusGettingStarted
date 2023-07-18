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
            movieEntity.setName("TestName_" + i);
            movieEntity.setDescription("TestDescription_" + i);
            movieEntity.setReasonsToView("TestReasonsToView_" + i);
            movieEntity.setFacts("TestFacts_" + i);
            movieEntity.setDurationInSeconds(ThreadLocalRandom.current().nextInt(60, 21601));
            movieEntity.setDistributor("TestDistributor_" + i);
            movieEntity.setCountry("TestCountry_" + i);
            movieEntity.setReleaseYear(ThreadLocalRandom.current().nextInt(1895, 2101));

            movieRepository.persist(movieEntity);
        }
    }

    @Override
    public void updateAllFacts() {
        List<MovieEntity> movieEntityList = movieRepository.findAll().list();
        for (MovieEntity entity : movieEntityList) {
            if (entity.getFacts() != null) {
                if (entity.getFacts().length() < 256)
                    entity.setFacts(entity.getFacts() + "\n- Blah-blah-blah. It is an additional fact.");
            } else {
                entity.setFacts("Not stated.");
            }
        }
    }
}
