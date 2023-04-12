package com.mpsdevelopment.uavsim.mongodb;

import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

/**
 * Just sets id for the baseobject before saving it
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class SaveWithIdMongoEventListener extends AbstractMongoEventListener<Object> {

    private final IdGenerator idGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        final Object source = event.getSource();
        if (source instanceof BaseDocument baseDocument && baseDocument.getId() == null) {
            baseDocument.setId(idGenerator.nextId());
        }
    }
}