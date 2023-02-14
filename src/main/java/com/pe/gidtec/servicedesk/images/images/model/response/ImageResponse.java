package com.pe.gidtec.servicedesk.images.images.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.bson.types.Binary;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ImageResponse")
public class ImageResponse {

    @Schema(name = "imageId",
            description = "El identificador de la imagen.",
            example = "63c49aefb24fdd6906a50232")
    private String imageId;

    @Schema(name = "originId",
            description = "El identificador del recurso al que pertenece la imagen (puede ser foto de perfil, archivo adjunto en mensaje, etc).",
            example = "63c49aefb24fdd6906a50232")
    private String originId;

    @Schema(name = "image",
            description = "La imagen en bytes.")
    private Binary image;
}
