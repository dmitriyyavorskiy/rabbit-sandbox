package com.mpsdevelopment.uavsim.mongodb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;

@Getter
@Setter
public class CascadeCallback implements ReflectionUtils.FieldCallback {

    private Object source;
    private MongoOperations mongoOperations;

    CascadeCallback(final Object source, final MongoOperations mongoOperations) {
        this.source = source;
        this.setMongoOperations(mongoOperations);
    }

    @Override
    public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.makeAccessible(field);

        if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
            final Object fieldValue = field.get(getSource());

            if (fieldValue != null) {
                final MongoFieldCallback callback = new MongoFieldCallback();
                ReflectionUtils.doWithFields(fieldValue.getClass(), callback);

                if (fieldValue instanceof Iterable) {
                    ((Collection) fieldValue).forEach(item ->
                        getMongoOperations().save(item)
                    );
                } else {
                    getMongoOperations().save(fieldValue);
                }
            }
        }

    }
}