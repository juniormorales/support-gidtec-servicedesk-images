package com.pe.gidtec.servicedesk.images.controller;

import com.pe.gidtec.servicedesk.images.images.model.response.ImageResponse;
import com.pe.gidtec.servicedesk.images.images.service.ImagesService;
import com.pe.gidtec.servicedesk.images.util.ResponseUtil;
import com.pe.gidtec.servicedesk.images.util.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/support/api/images")
@Tag(name = "Images", description = "Gestiona las imagenes enviadas por el usuario")
@Slf4j
@Validated
public class ImagesController {

    private final ImagesService imagesService;

    private final ResponseUtil responseUtil;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Proceso Satisfactorio.",
                    content = @Content(schema = @Schema(implementation = ResultResponse.class)))
    },
            summary = "Permite guardar un Imagen segun el recurso de donde proviene (mensaje,perfil,etc)",
            method = "POST")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ResponseEntity<ResultResponse<String>>> create(
            @RequestPart("originId")
                    String originId,
            @RequestPart("image") Mono<FilePart> image) {
        return image.doOnNext(fp -> log.info("Received file : {}", fp.filename()))
                .flatMap(fp -> imagesService.createImage(originId, fp)
                        .map(responseUtil::getResponseEntityStatus));
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Proceso Satisfactorio.",
                    content = @Content(schema = @Schema(implementation = ImageResponse.class)))
    },
            summary = "Permite obtener una lista de identificadores de imagen pertenecientes a un recurso",
            method = "GET")
    @GetMapping(value = "/search/{originId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ResponseEntity<ResultResponse<List<ImageResponse>>>> getAllIds(
            @Parameter(name = "originId", example = "s58xe8d0x6", description = "Identificador del recurso donde proviene la imagen")
            @PathVariable("originId")
            @NotBlank(message = "El valor de 'originId' no puede ser vacio.")
                    String originId) {
        return imagesService.findImageIdsByOriginId(originId)
                .map(responseUtil::getResponseEntityStatus);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Proceso Satisfactorio.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    },
            summary = "Permite obtener la imagen mediante su identificador 'imageId'",
            method = "GET")
    @GetMapping(value = "/{imageId}", produces = {
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_GIF_VALUE
    })
    public Mono<DataBuffer> doGet(
            @PathVariable("imageId")
                    String imageId) {
        try {
            return imagesService.getImageById(imageId)
                    .map(response -> {
                        byte[] barr = response.getImage().getData();
                        DataBufferFactory bufferFactory = new DefaultDataBufferFactory();
                        DataBuffer buffer = bufferFactory.allocateBuffer(barr.length);
                        buffer.write(barr);
                        return buffer;
                    });

        } catch (Exception e) {
            log.error("An unknown IO error occurred while writing pixel", e);
        }
        return Mono.empty();
    }
}
