package edu.my.exception.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class GenericJDBCExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<GenericJDBCException> {
    final Logger logger = LoggerFactory.getLogger(GenericJDBCExceptionMapper.class);

    @Override
    public Response toResponse(GenericJDBCException e) {
        logger.error("Возникла ошибка при попытке связаться с базой данных", e);
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
    }
}
