package com.caixabank.loansmanager.infrastructure.adapters.inbound.exceptionhandler;

import com.caixabank.loansmanager.domain.exceptions.LoanApplicationNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.RollbackException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.ValidationException;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HandlerExceptionControllerTest {

    private final HandlerExceptionController handler = new HandlerExceptionController();
    private final WebRequest request = mock(WebRequest.class);

    // ========== MethodArgumentTypeMismatchException ==========

    @Test
    void handleMethodArgumentTypeMismatch_WithRequiredType_ShouldReturnBadRequest() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("param");
        when(ex.getValue()).thenReturn("value");
        doReturn(Integer.class).when(ex).getRequiredType();

        ResponseEntity<Object> response = handler.handleMethodArgumentTypeMismatch(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertEquals("MethodArgumentTypeMismatchException: Invalid Argument Type", body.getTitle());
        assertTrue(body.getDetail().contains("Integer"));
    }

    @Test
    void handleMethodArgumentTypeMismatch_WithNullRequiredType_ShouldReturnBadRequest() {
        // Test the branch where getRequiredType() returns null
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("param");
        when(ex.getValue()).thenReturn("value");
        doReturn(null).when(ex).getRequiredType();

        ResponseEntity<Object> response = handler.handleMethodArgumentTypeMismatch(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertTrue(body.getDetail().contains("a different type"));
    }

    // ========== UnexpectedTypeException ==========

    @Test
    void handleUnexpectedType_ShouldReturnBadRequest() {
        UnexpectedTypeException ex = new UnexpectedTypeException("Unexpected type");

        ResponseEntity<Object> response = handler.handleUnexpectedType(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertEquals("UnexpectedTypeException: Unexpected Validation Type", body.getTitle());
    }

    // ========== IllegalArgumentException ==========

    @Test
    void handleIllegalArgument_WithMessage_ShouldReturnBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException("Bad arg");

        ResponseEntity<Object> response = handler.handleIllegalArgument(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertEquals("Bad arg", body.getDetail());
    }

    @Test
    void handleIllegalArgument_WithNullMessage_ShouldReturnDefaultMessage() {
        // Test the branch where getMessage() returns null
        IllegalArgumentException ex = new IllegalArgumentException((String) null);

        ResponseEntity<Object> response = handler.handleIllegalArgument(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertTrue(body.getDetail().contains("business rules"));
    }

    // ========== LoanApplicationNotFoundException ==========

    @Test
    void handleLoanApplicationNotFound_WithMessage_ShouldReturnNotFound() {
        LoanApplicationNotFoundException ex = new LoanApplicationNotFoundException("Not found");

        ResponseEntity<Object> response = handler.handleUserNotFound(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertEquals("Not found", body.getDetail());
    }

    @Test
    void handleLoanApplicationNotFound_WithNullMessage_ShouldReturnDefaultMessage() {
        LoanApplicationNotFoundException ex = new LoanApplicationNotFoundException(null);

        ResponseEntity<Object> response = handler.handleUserNotFound(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertTrue(body.getDetail().contains("was not found"));
    }

    // ========== EmptyResultDataAccessException ==========

    @Test
    void handleEmptyResultDataAccess_ShouldReturnNotFound() {
        EmptyResultDataAccessException ex = new EmptyResultDataAccessException(1);

        ResponseEntity<Object> response = handler.handleEmptyResultDataAccess(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertEquals("EmptyResultDataAccessException: Resource Not Found", body.getTitle());
    }

    // ========== DataIntegrityViolationException ==========

    @Test
    void handleDataIntegrityViolation_ShouldReturnConflict() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Conflict");

        ResponseEntity<Object> response = handler.handleDataIntegrityViolation(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertEquals("DataIntegrityViolationException: Data Conflict", body.getTitle());
    }

    // ========== HttpMessageNotReadableException ==========

    @Test
    void handleHttpMessageNotReadable_WithRootCause_ShouldReturnBadRequest() {
        Throwable rootCause = new RuntimeException("JSON parse error");
        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
        when(ex.getRootCause()).thenReturn(rootCause);

        ResponseEntity<Object> response = handler.handleHttpMessageNotReadable(
                ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertEquals("JSON parse error", body.getDetail());
    }

    @Test
    void handleHttpMessageNotReadable_WithNullRootCause_ShouldReturnDefaultMessage() {
        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
        when(ex.getRootCause()).thenReturn(null);

        ResponseEntity<Object> response = handler.handleHttpMessageNotReadable(
                ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertTrue(body.getDetail().contains("malformed"));
    }

    // ========== ConstraintViolationException ==========

    @Test
    void handleConstraintViolation_ShouldReturnBadRequest() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);
        when(path.toString()).thenReturn("fieldName");
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("must not be null");

        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(violation);

        ConstraintViolationException ex = new ConstraintViolationException("Validation failed", violations);

        ResponseEntity<Object> response = handler.handleConstraintViolation(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertEquals("ConstraintViolationException: Constraint Violation", body.getTitle());
        assertNotNull(body.getProperties().get("errors"));
    }

    // ========== MethodArgumentNotValidException ==========

    @Test
    void handleMethodArgumentNotValid_WithDefaultMessage_ShouldReturnBadRequest() throws NoSuchMethodException {
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "target");
        bindingResult.addError(new FieldError("target", "field1", "must not be null"));

        MethodParameter methodParameter = new MethodParameter(
                Object.class.getMethod("toString"), -1);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(
                ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertEquals("MethodArgumentNotValidException: Validation Error", body.getTitle());
    }

    @Test
    void handleMethodArgumentNotValid_WithNullDefaultMessage_ShouldReturnInvalidValue() throws NoSuchMethodException {
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "target");
        bindingResult.addError(new FieldError("target", "field1", null, false, null, null, null));

        MethodParameter methodParameter = new MethodParameter(
                Object.class.getMethod("toString"), -1);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(
                ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ========== ValidationException ==========

    @Test
    void handleValidation_WithMessage_ShouldReturnBadRequest() {
        ValidationException ex = new ValidationException("Validation failed");

        ResponseEntity<Object> response = handler.handleValidation(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertEquals("Validation failed", body.getDetail());
    }

    @Test
    void handleValidation_WithNullMessage_ShouldReturnDefaultMessage() {
        ValidationException ex = new ValidationException((String) null);

        ResponseEntity<Object> response = handler.handleValidation(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertTrue(body.getDetail().contains("validation rules"));
    }

    @Test
    void handleValidation_WithEmptyMessage_ShouldReturnDefaultMessage() {
        ValidationException ex = new ValidationException("   ");

        ResponseEntity<Object> response = handler.handleValidation(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertTrue(body.getDetail().contains("validation rules"));
    }

    // ========== RollbackException ==========

    @Test
    void handleRollBack_ShouldReturnInternalServerError() {
        RollbackException ex = new RollbackException("Rollback");

        ResponseEntity<Object> response = handler.handleRollBack(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertEquals("RollbackException: Internal Server Error", body.getTitle());
    }

    // ========== HttpRequestMethodNotSupportedException ==========

    @Test
    void handleHttpRequestMethodNotSupported_WithMessage_ShouldReturnMethodNotAllowed() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("POST");

        ResponseEntity<Object> response = handler.handleHttpRequestMethodNotSupported(
                ex, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertEquals("HttpRequestMethodNotSupportedException: HTTP Method Not Allowed", body.getTitle());
    }

    @Test
    void handleHttpRequestMethodNotSupported_WithSupportedMethods_ShouldIncludeAllowedMethods() {
        HttpRequestMethodNotSupportedException ex = mock(HttpRequestMethodNotSupportedException.class);
        when(ex.getMessage()).thenReturn("Request method 'POST' is not supported");
        when(ex.getSupportedHttpMethods()).thenReturn(Set.of(HttpMethod.GET, HttpMethod.PUT));

        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> response = handler.handleHttpRequestMethodNotSupported(
                ex, headers, HttpStatus.METHOD_NOT_ALLOWED, request);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertNotNull(body.getProperties().get("allowedMethods"));
    }

    @Test
    void handleHttpRequestMethodNotSupported_WithNullMessage_ShouldReturnDefaultMessage() {
        HttpRequestMethodNotSupportedException ex = mock(HttpRequestMethodNotSupportedException.class);
        when(ex.getMessage()).thenReturn(null);
        when(ex.getSupportedHttpMethods()).thenReturn(null);

        ResponseEntity<Object> response = handler.handleHttpRequestMethodNotSupported(
                ex, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertTrue(body.getDetail().contains("not supported"));
    }

    @Test
    void handleHttpRequestMethodNotSupported_WithEmptyMessage_ShouldReturnDefaultMessage() {
        HttpRequestMethodNotSupportedException ex = mock(HttpRequestMethodNotSupportedException.class);
        when(ex.getMessage()).thenReturn("   ");
        when(ex.getSupportedHttpMethods()).thenReturn(null);

        ResponseEntity<Object> response = handler.handleHttpRequestMethodNotSupported(
                ex, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertTrue(body.getDetail().contains("not supported"));
    }

    @Test
    void handleHttpRequestMethodNotSupported_WithEmptySupportedMethods_ShouldNotIncludeAllowedMethods() {
        HttpRequestMethodNotSupportedException ex = mock(HttpRequestMethodNotSupportedException.class);
        when(ex.getMessage()).thenReturn("POST method not supported");
        when(ex.getSupportedHttpMethods()).thenReturn(Set.of());

        ResponseEntity<Object> response = handler.handleHttpRequestMethodNotSupported(
                ex, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        // Properties may be null or not contain allowedMethods when empty set is
        // provided
        assertTrue(body.getProperties() == null || !body.getProperties().containsKey("allowedMethods"));
    }

    // ========== PropertyReferenceException ==========

    @Test
    void handlePropertyReference_WithMessage_ShouldReturnBadRequest() {
        PropertyReferenceException ex = mock(PropertyReferenceException.class);
        when(ex.getMessage()).thenReturn("Invalid property");
        when(ex.getPropertyName()).thenReturn("prop");

        ResponseEntity<Object> response = handler.handlePropertyReference(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertEquals("Invalid property", body.getDetail());
    }

    @Test
    void handlePropertyReference_WithNullMessage_ShouldReturnDefaultMessage() {
        PropertyReferenceException ex = mock(PropertyReferenceException.class);
        when(ex.getMessage()).thenReturn(null);
        when(ex.getPropertyName()).thenReturn("prop");

        ResponseEntity<Object> response = handler.handlePropertyReference(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertTrue(body.getDetail().contains("does not exist"));
    }

    @Test
    void handlePropertyReference_WithEmptyMessage_ShouldReturnDefaultMessage() {
        PropertyReferenceException ex = mock(PropertyReferenceException.class);
        when(ex.getMessage()).thenReturn("   ");
        when(ex.getPropertyName()).thenReturn("prop");

        ResponseEntity<Object> response = handler.handlePropertyReference(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertTrue(body.getDetail().contains("does not exist"));
    }
}
