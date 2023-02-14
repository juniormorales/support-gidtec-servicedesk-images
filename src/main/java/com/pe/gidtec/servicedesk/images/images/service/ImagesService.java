package com.pe.gidtec.servicedesk.images.images.service;

import com.pe.gidtec.servicedesk.images.images.model.response.ImageResponse;
import com.pe.gidtec.servicedesk.images.util.ResultResponse;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ImagesService {

    Mono<ResultResponse<String>> createImage(String originId, FilePart multipartFile);

    Mono<ResultResponse<List<ImageResponse>>> findImageIdsByOriginId(String originId);

    Mono<ImageResponse> getImageById(String imageId);
}
