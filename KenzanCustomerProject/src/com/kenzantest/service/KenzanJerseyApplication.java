package com.kenzantest.service;

import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.ValidationConfig;
import org.glassfish.jersey.server.validation.internal.InjectingConstraintValidatorFactory;
/**
 * ContactCard application configuration
 * JAX-RS application
 */
public class KenzanJerseyApplication extends ResourceConfig{

    public KenzanJerseyApplication(){
        // Resource Package Address 
        packages("com.kenzantest");
        //Validation.
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        register(ValidationConfigurationContextResolver.class);

    }
    public static class ValidationConfigurationContextResolver implements ContextResolver<ValidationConfig>{
        @Context
        private ResourceContext resourceContext;
        @Override
        public ValidationConfig getContext(final Class<?> type) {
            return new ValidationConfig().constraintValidatorFactory(resourceContext.getResource(InjectingConstraintValidatorFactory.class));
        }
    }
}