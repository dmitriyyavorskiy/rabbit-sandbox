package com.mpsdevelopment.uavsim.mongodb.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class RepositoryHelper {

    private final MongoTemplate mongoTemplate;

    public long countByCriteria(Class<?> clazz, List<Criteria> criteriaList) {
        Query query = new Query();
        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }
        return mongoTemplate.count(query, clazz);
    }

    public <T> Page<T> executePageableWithFilter(Pageable pageable, Collection<Criteria> criteria, Class<T> documentClass) {
        return executePageableWithFilter(new Query(), pageable, criteria, documentClass);
    }

    public <T> Page<T> executePageableWithFilter(Query query, Pageable pageable, Collection<Criteria> criteria, Class<T> documentClass) {
        var queryCriteria = criteria.isEmpty() ?
                new Criteria() :
                new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()]));

        List<T> entities = mongoTemplate.find(query.with(pageable).addCriteria(queryCriteria), documentClass);
        return PageableExecutionUtils.getPage(
                entities,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), documentClass));
    }

    public List<Criteria> formCriteriaOptionalFilterByFields(List<String> fields, Object... params) {
        if (fields.size() != params.length) {
            throw new IllegalArgumentException("Both params should have the same size");
        }
        var filters = new ArrayList<Criteria>();
        IntStream.range(0, fields.size())
                .mapToObj(i -> Optional.ofNullable(params[i]).map(filterParam -> Criteria.where(fields.get(i)).is(filterParam)))
                .forEach(maybeFilter -> maybeFilter.ifPresent(filters::add));
        return filters;
    }
}
