package com.pe.gidtec.servicedesk.images.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResponseUtil {

    public <T> ResponseEntity<ResultResponse<T>> getResponseEntityStatus(ResultResponse<T> response) {
        if( response.getResponseCode().equals("00")) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setData(null);
            switch (response.getResponseCode()) {
                case "02":
                case "04":
                case "05": return ResponseEntity
                        .status(HttpStatus.PRECONDITION_FAILED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(response);
                default: return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(response);
            }
        }
    }
}
