package edu.my.resource.impl;

import edu.my.API.resource.MassiveMovieResource;
import edu.my.service.controller_layer.MassiveMovieProcessingControllerService;
import edu.my.service.controller_layer.MovieControllerService;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/massive_movie")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@IfBuildProfile("teststand")
public class MassiveMovieResourceImpl implements MassiveMovieResource {
    @Inject
    MassiveMovieProcessingControllerService massiveMovieProcessingControllerService;
    @Inject
    MovieControllerService movieControllerService;

    @Override
    @Transactional
    @Counted(name = "performedAddMassiveMovie", description = "How many massive add was called")
    @Timed(name = "addMassiveMovieTimer", description = "A measure of how long it takes to add all movies", unit = MetricUnits.MILLISECONDS)
    public Response add(int count) {
        massiveMovieProcessingControllerService.add(count);
        return Response.status(Response.Status.CREATED).entity(movieControllerService.getAll()).build();
    }

    @Override
    @Transactional
    @Counted(name = "performedUpdateMassiveMovie", description = "How many massive update was called")
    @Timed(name = "updateMassiveMovieTimer", description = "A measure of how long it takes to update all movies", unit = MetricUnits.MILLISECONDS)
    public Response updateAllFacts() {
        massiveMovieProcessingControllerService.updateAllFacts();
        return Response.status(Response.Status.CREATED).entity(movieControllerService.getAll()).build();
    }
}
