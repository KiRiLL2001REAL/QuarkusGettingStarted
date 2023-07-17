package edu.my.exception.mapper;

import edu.my.exception.TagIsAlreadyBoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class TagIsAlreadyBoundExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<TagIsAlreadyBoundException> {
    final Logger logger = LoggerFactory.getLogger(TagIsAlreadyBoundExceptionMapper.class);

    @Override
    public Response toResponse(TagIsAlreadyBoundException e) {
        logger.warn("Попытка добавить фильму тег, который уже имеется: " + e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
