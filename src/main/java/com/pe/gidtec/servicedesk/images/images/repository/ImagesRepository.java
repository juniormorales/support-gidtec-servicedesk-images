package com.pe.gidtec.servicedesk.images.images.repository;

import com.pe.gidtec.servicedesk.images.images.model.entity.ImageEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ImagesRepository extends ReactiveMongoRepository<ImageEntity, String> {

    Flux<ImageEntity> findAllByOriginId(String originId);
}
