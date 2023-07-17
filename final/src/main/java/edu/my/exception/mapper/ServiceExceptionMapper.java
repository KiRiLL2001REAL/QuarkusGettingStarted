package edu.my.exception.mapper;

import edu.my.exception.TagIsAlreadyBoundException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ServiceExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<Exception> {
    final Logger logger = LoggerFactory.getLogger(ServiceExceptionMapper.class);

    @Override
    public Response toResponse(Exception e) {
        if (e instanceof GenericJDBCException) {
            logger.error("Возникла ошибка при попытке связаться с базой данных", e);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else if (e instanceof NotFoundException) {
            logger.warn("Не удалось найти сущность в БД: " + e.getMessage());
        } else if (e instanceof TagIsAlreadyBoundException) {
            logger.warn("Попытка добавить фильму тег, который уже имеется: " + e.getMessage());
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
