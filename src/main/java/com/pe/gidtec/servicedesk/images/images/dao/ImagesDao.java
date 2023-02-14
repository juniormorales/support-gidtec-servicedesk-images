package com.pe.gidtec.servicedesk.images.images.dao;

import com.pe.gidtec.servicedesk.images.images.model.entity.ImageEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImagesDao {

    Flux<ImageEntity> findAllImagesByOrigin(String originId);

    Mono<ImageEntity> findImageById(String imageId);

    Mono<Void> deleteImageById(String imageId);

    Mono<ImageEntity> saveImage(ImageEntity entity);

    Mono<Boolean> existsById(String imageId);
}
