package com.mpsdevelopment.uavsim.mongodb.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpsdevelopment.uavsim.mongodb.BaseDocument;
import com.mpsdevelopment.uavsim.mongodb.enums.RoleName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "user")
@TypeAlias("user")
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends BaseDocument implements Serializable {

    @Indexed
    @EqualsAndHashCode.Include
    private String login;

    private String password;

    @Indexed
    private String apiKey;

    private RoleName userRole = RoleName.USER;

    private Long correspondingPerson;

    private boolean active = true;
}