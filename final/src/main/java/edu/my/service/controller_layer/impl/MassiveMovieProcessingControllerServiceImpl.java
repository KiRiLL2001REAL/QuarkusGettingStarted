package edu.my.service.controller_layer.impl;

import edu.my.service.controller_layer.MassiveMovieProcessingControllerService;
import edu.my.service.logic_layer.MassiveMovieProcessingService;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@IfBuildProfile("teststand")
public class MassiveMovieProcessingControllerServiceImpl implements MassiveMovieProcessingControllerService {
    @Inject
    MassiveMovieProcessingService massiveMovieService;

    @Override
    public void add(Integer count) {
        massiveMovieService.add(count);
    }

    @Override
    public void updateAllFacts() {
        massiveMovieService.updateAllFacts();
    }
}
