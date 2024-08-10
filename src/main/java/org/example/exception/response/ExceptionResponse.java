package org.example.exception.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ExceptionResponse {

    private String errorMessage;
    private String rootErrorMessage;
    private String callerURL;

}
