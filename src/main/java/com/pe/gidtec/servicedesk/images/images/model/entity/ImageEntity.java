package com.pe.gidtec.servicedesk.images.images.model.entity;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "images")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {

    @Id
    private String imageId;

    @Field(name = "origin_id")
    private String originId;

    private Binary image;
}
