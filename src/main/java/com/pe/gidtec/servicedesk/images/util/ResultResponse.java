package com.pe.gidtec.servicedesk.images.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "ResultResponse")
public class ResultResponse<T>{

    @Schema(name = "data",
            description = "Objeto de respuesta.")
    private T data;

    @Schema(name = "responseCode",
            description = "Código de respuesta.",
            example = "00")
    private String responseCode;

    @Schema(name = "responseMessage",
            description = "Mensaje de respuesta",
            example = "La solicitud ha tenido éxito")
    private String responseMessage;

    public static <T> ResultResponse<T> ok(T data){
        return ResultResponse.<T>builder()
                .data(data)
                .responseCode(ResponseStatus.OK.getCode())
                .responseMessage(ResponseStatus.OK.getDescription())
                .build();
    }

    public static <T> ResultResponse<T> noData(){
        return ResultResponse.<T>builder()
                .responseCode(ResponseStatus.OK.getCode())
                .responseMessage(ResponseStatus.OK.getDescription())
                .build();
    }
    public static <T> ResultResponse<T> error(ResponseStatus status){
        return ResultResponse.<T>builder()
                .data(null)
                .responseCode(status.getCode())
                .responseMessage(status.getDescription())
                .build();
    }
}
