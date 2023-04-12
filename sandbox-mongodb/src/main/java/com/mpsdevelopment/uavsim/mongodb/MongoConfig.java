package com.mpsdevelopment.uavsim.mongodb;

import com.mpsdevelopment.uavsim.mongodb.converter.DateToOffsetDateTimeConverter;
import com.mpsdevelopment.uavsim.mongodb.converter.OffsetDateTimeToDateConverter;
import com.mpsdevelopment.uavsim.mongodb.converter.OffsetTimeConverter;
import com.mongodb.DBRef;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MongoConfig {

    private final MongoDatabaseFactory mongoDatabaseFactory;

    private final MongoMappingContext mongoMappingContext;

    @Bean
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }

    @Bean
    public MongoTemplate mongoTemplate(MappingMongoConverter mongoConverter) {
        return new MongoTemplate(mongoDatabaseFactory, mongoConverter);
    }

    @Bean
    public MappingMongoConverter mongoConverter(IdGenerator idGenerator) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        mongoMappingContext.setAutoIndexCreation(true);
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext) {

            @Override
            protected DBRef createDBRef(Object target, MongoPersistentProperty property) {
                // we should set id before save
                if (target instanceof BaseDocument baseDocument && baseDocument.getId() == null) {
                    baseDocument.setId(idGenerator.nextId());
                }
                return super.createDBRef(target, property);
            }

            @Override
            protected void writeInternal(@Nullable Object obj, Bson bson, @Nullable TypeInformation<?> typeHint) {
                // we should set id before save
                log.debug("Write internal for {}", obj);
                if (obj instanceof BaseDocument baseDocument) {
                    if (baseDocument.getId() == null) {
                        baseDocument.setId(idGenerator.nextId());
                    }
                    if (baseDocument.getCreatedDate() == null) {
                        baseDocument.setCreatedDate(OffsetDateTime.now(ZoneOffset.UTC));
                    }
                    baseDocument.setUpdatedDate(OffsetDateTime.now(ZoneOffset.UTC));
                }
                super.writeInternal(obj, bson, typeHint);
            }

        };

        mongoConverter.setCustomConversions(new MongoCustomConversions(customConversions()));

        return mongoConverter;
    }

    protected List<Converter<?, ?>> customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new DateToOffsetDateTimeConverter());
        converterList.add(new OffsetDateTimeToDateConverter());
        converterList.add(new OffsetTimeConverter.Serializer());
        converterList.add(new OffsetTimeConverter.Deserializer());
        return converterList;
    }

    @Bean
    public SaveWithIdMongoEventListener saveWithIdMongoEventListener(IdGenerator idGenerator) {
        return new SaveWithIdMongoEventListener(idGenerator);
    }

    @Bean
    public CascadeSaveMongoEventListener cascadeSaveMongoEventListener() {
        return new CascadeSaveMongoEventListener();
    }
}