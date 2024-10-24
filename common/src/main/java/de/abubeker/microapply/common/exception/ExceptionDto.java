package de.abubeker.microapply.common.exception;

import java.time.LocalDateTime;

public record ExceptionDto (Integer statusCode, String message, LocalDateTime timestamp) { }
