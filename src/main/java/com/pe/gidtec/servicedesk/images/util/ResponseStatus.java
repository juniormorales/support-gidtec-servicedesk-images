package com.pe.gidtec.servicedesk.images.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    OK("00", "la solicitud ha tenido éxito."),
    NO_DATA("00", "No hay registros para la busqueda seleccionada"),
    ERROR_SAVE_IMAGE("01", "Ocurrió un error al intentar guardar la imagen");

    private final String code;
    private final String description;
}
