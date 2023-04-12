package com.mpsdevelopment.uavsim.mongodb.utils;

import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.Assertions;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

@UtilityClass
public class TestUtils {

    public <T> T findFirstByPredicate(Collection<T> collection, Predicate<T> predicate) {
        Optional<T> foundValue = collection
                .stream()
                .filter(predicate)
                .findFirst();
        Assertions.assertTrue(foundValue.isPresent());
        return foundValue.get();
    }
}
