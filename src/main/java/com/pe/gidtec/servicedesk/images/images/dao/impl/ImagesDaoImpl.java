package com.pe.gidtec.servicedesk.images.images.dao.impl;

import com.pe.gidtec.servicedesk.images.images.dao.ImagesDao;
import com.pe.gidtec.servicedesk.images.images.model.entity.ImageEntity;
import com.pe.gidtec.servicedesk.images.images.repository.ImagesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImagesDaoImpl implements ImagesDao {

    private final ImagesRepository imagesRepository;


    @Override
    public Flux<ImageEntity> findAllImagesByOrigin(String originId) {
        return imagesRepository.findAllByOriginId(originId);
    }

    @Override
    public Mono<ImageEntity> findImageById(String imageId) {
        return imagesRepository.findById(imageId);
    }

    @Override
    public Mono<Void> deleteImageById(String imageId) {
        return imagesRepository.deleteById(imageId);
    }

    @Override
    public Mono<ImageEntity> saveImage(ImageEntity entity) {
        return imagesRepository.save(entity)
                .doOnSuccess(message -> log.info("Imagen " + message.toString() + " guardada con éxito."))
                .onErrorResume(ex -> Mono.error(new Exception("Ocurrio un error al intentar guardar la imagen")));
    }

    @Override
    public Mono<Boolean> existsById(String imageId) {
        return imagesRepository.existsById(imageId);
    }
}
