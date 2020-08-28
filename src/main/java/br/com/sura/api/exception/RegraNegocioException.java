package br.com.sura.api.exception;

import lombok.Getter;

@Getter
public class RegraNegocioException extends RuntimeException {

    private static final long serialVersionUID = -7806029002430564887L;

    private final String message;

    public RegraNegocioException(String message) {
        this.message = message;
    }
}
