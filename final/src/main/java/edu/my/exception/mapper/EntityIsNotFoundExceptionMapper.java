package edu.my.exception.mapper;

import edu.my.exception.EntityIsNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class EntityIsNotFoundExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<EntityIsNotFoundException> {
    final Logger logger = LoggerFactory.getLogger(EntityIsNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(EntityIsNotFoundException e) {
        logger.warn("Не удалось найти сущность в БД: " + e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
