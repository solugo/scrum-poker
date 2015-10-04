package de.solugo.scrumpoker.util;

import de.solugo.scrumpoker.data.entity.Account;
import de.solugo.scrumpoker.data.entity.Base;
import de.solugo.scrumpoker.rest.resource.AccountResource;
import de.solugo.scrumpoker.rest.resource.BaseResource;
import de.solugo.scrumpoker.service.SecurityService;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.dozer.loader.api.TypeMappingOptions;

import java.util.ArrayList;
import java.util.List;

public class BeanMapper {
    private final DozerBeanMapper defaultMapper = new DozerBeanMapper();
    private final DozerBeanMapper notNullMapper = new DozerBeanMapper();

    public BeanMapper() {
        defaultMapper.addMapping(new DefaultMapping());
        notNullMapper.addMapping(new DefaultMapping());
        notNullMapper.addMapping(new NotNullMapping());
    }

    public <T> T map(final Object source, final Class<T> type) {
        return mapInternal(source, type, defaultMapper);
    }

    public <T> T mapNotNull(final Object source, final Class<T> type) {
        return mapInternal(source, type, notNullMapper);
    }

    private <T> T mapInternal(final Object source, final Class<T> type, final Mapper mapper) {
        return mapper.map(source, type);
    }

    public <T> T map(final Object source, final T target) {
        return mapInternal(source, target, defaultMapper);
    }

    public <T> T mapNotNull(final Object source, final T target) {
        return mapInternal(source, target, notNullMapper);
    }

    private <T> T mapInternal(final Object source, final T target, final Mapper mapper) {
        mapper.map(source, target);
        return target;
    }

    public <T> List<T> map(final List<?> sources, final Class<T> type) {
        return mapInternal(sources, type, defaultMapper);
    }

    public <T> List<T> mapNotNull(final List<?> sources, final Class<T> type) {
        return mapInternal(sources, type, notNullMapper);
    }

    private <T> List<T> mapInternal(final List<?> sources, final Class<T> type, final Mapper mapper) {
        final List<T> result = new ArrayList<>();
        for (final Object source : sources) {
            result.add(mapper.map(source, type));
        }
        return result;
    }

    private static class DefaultMapping extends BeanMappingBuilder {

        @Override
        protected void configure() {
            mapping(Base.class, BaseResource.class)
                .fields(
                    "id",
                    "id",
                    FieldsMappingOptions.oneWay()
                );
            mapping(AccountResource.class, Account.class)
                .fields(
                    "password",
                    "password",
                    FieldsMappingOptions.oneWay(),
                    FieldsMappingOptions.customConverter(PasswordConverter.class)
                );
        }

    }

    public static class PasswordConverter implements CustomConverter {

        @Override
        public Object convert(final Object existingDestinationFieldValue, final Object sourceFieldValue, final Class<?> destinationClass, final Class<?> sourceClass) {
            if (sourceFieldValue != null) {
                return SecurityService.PASSWORD_ENCODER.encode(String.valueOf(sourceFieldValue));
            } else {
                return existingDestinationFieldValue;
            }
        }

    }

    public static class NotNullMapping extends BeanMappingBuilder {

        @Override
        protected void configure() {
            this.mapping(Object.class, Object.class, TypeMappingOptions.mapNull(false));
        }

    }

}
