package com.pe.gidtec.servicedesk.images.images.service.impl;

import com.pe.gidtec.servicedesk.images.images.dao.ImagesDao;
import com.pe.gidtec.servicedesk.images.images.model.entity.ImageEntity;
import com.pe.gidtec.servicedesk.images.images.model.response.ImageResponse;
import com.pe.gidtec.servicedesk.images.images.service.ImagesService;
import com.pe.gidtec.servicedesk.images.util.ResponseStatus;
import com.pe.gidtec.servicedesk.images.util.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ImagesServiceImpl implements ImagesService {

    private final ImagesDao imagesDao;

    @Override
    public Mono<ResultResponse<String>> createImage(String originId, FilePart multipartFile) {
        log.info("createImage -> {}", originId);
        return buildRequestToEntity(originId, multipartFile)
                .flatMap(imagesDao::saveImage)
                .map(entity -> ResultResponse.ok(entity.getImageId()))
                .onErrorReturn(ResultResponse.error(ResponseStatus.ERROR_SAVE_IMAGE));
    }

    @Override
    public Mono<ResultResponse<List<ImageResponse>>> findImageIdsByOriginId(String originId) {
        return imagesDao.findAllImagesByOrigin(originId)
                .map(entity -> ImageResponse.builder()
                        .originId(entity.getOriginId())
                        .imageId(entity.getImageId())
                        .build())
                .collectList()
                .map(list -> {
                    log.info("list -> {}", list);
                    if(list.isEmpty()){
                        return ResultResponse.noData();
                    } else {
                        return ResultResponse.ok(list);
                    }
                });
    }

    @Override
    public Mono<ImageResponse> getImageById(String imageId) {
        return imagesDao.findImageById(imageId)
                .map(entity -> ImageResponse.builder()
                        .imageId(entity.getImageId())
                        .originId(entity.getOriginId())
                        .image(entity.getImage())
                        .build());
    }

    private Mono<ImageEntity> buildRequestToEntity(String originId, FilePart filePart) {
        return toBytes(filePart)
                .map(bytes -> ImageEntity.builder()
                        .originId(originId)
                        .image(new Binary(bytes))
                        .build());
    }


    private Mono<byte[]> toBytes(FilePart filePart) {
        var byteStream = new ByteArrayOutputStream();
        return filePart.content()
                .flatMap(dataBuffer -> Flux.just(dataBuffer.asByteBuffer().array()))
                .doOnNext(bytes -> {
                    try {
                        byteStream.write(bytes);
                    } catch (IOException e) {
                        log.error("toBytes -> {}", e.getMessage());
                    }
                })
                .collectList()
                .map(list -> byteStream.toByteArray());
    }
}
