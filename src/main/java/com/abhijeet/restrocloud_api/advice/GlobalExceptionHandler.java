package com.abhijeet.restrocloud_api.advice;

import com.abhijeet.restrocloud_api.dto.ApiResponse;
import com.abhijeet.restrocloud_api.exception.BadRequestException;
import com.abhijeet.restrocloud_api.exception.DuplicateResourceException;
import com.abhijeet.restrocloud_api.exception.EmptyInputException;
import com.abhijeet.restrocloud_api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

      // ================================================================
      // 400 - BAD REQUEST (Business logic error)
      // Triggered when YOU manually throw:
      // throw new BadRequestException("Some message");
      // Example: Email already exists, invalid business rule
      // ================================================================
      @ExceptionHandler(BadRequestException.class)
      public ResponseEntity<ApiResponse<?>> handleBadRequest(BadRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .data(null)
                            .build());
      }

      // ================================================================
      // 404 - NOT FOUND
      // Triggered when YOU manually throw:
      // ================================================================
      @ExceptionHandler(ResourceNotFoundException.class)
      public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .data(null)
                            .build());
      }

      // ================================================================
      // 400 - VALIDATION ERROR
      // Triggered AUTOMATICALLY by Spring when:
      // @Valid fails in Controller
      // Example:
      // @NotBlank, @Email, @Size validation fails in DTO
      // You DO NOT throw this manually
      // ================================================================
      @ExceptionHandler(MethodArgumentNotValidException.class)
      public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException ex) {

            // Get first validation error message
            String errorMessage = ex.getBindingResult()
                    .getFieldError()
                    .getDefaultMessage();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.builder()
                            .success(false)
                            .message(errorMessage)
                            .data(null)
                            .build());
      }

      // ================================================================
      // 400 - EMPTY INPUT (Optional custom exception)
      // Triggered when YOU manually throw:
      // throw new EmptyInputException("Name cannot be empty");
      // Note: This can also be handled using BadRequestException
      // ================================================================
      @ExceptionHandler(EmptyInputException.class)
      public ResponseEntity<ApiResponse<?>> handleEmptyInput(EmptyInputException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .data(null)
                            .build());
      }

      //409 -> Conflict , or Resource alredy existed
      @ExceptionHandler(DuplicateResourceException.class)
      public ResponseEntity<ApiResponse<?>> handleDeplicateResource(DuplicateResourceException duplicateResourceException){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.builder()
                            .success(false)
                            .message(duplicateResourceException.getMessage())
                            .data(null)
                            .build());
      }

      @ExceptionHandler(UsernameNotFoundException.class)
      public ResponseEntity<ApiResponse<?>> handleUserNotFoundInUserDetailAuthentication(UsernameNotFoundException usernameNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .success(false)
                            .message(usernameNotFoundException.getMessage())
                            .data(null)
                            .build());
      }

      // Handle IllegalArgumentException
      @ExceptionHandler(IllegalArgumentException.class)
      public ResponseEntity<ApiResponse<?>> handleIllegalArgument(IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.builder()
                            .success(false)
                            .message("Invalid argument: "+ex.getMessage())
                            .data(null)
                            .build()
                    );
      }

      @ExceptionHandler(IllegalStateException.class)
      public ResponseEntity<ApiResponse<?>> handleIllegalStateException(IllegalStateException illegalStateException){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.builder()
                            .success(false)
                            .message("Invalid argument: "+illegalStateException.getMessage())
                            .data(null)
                            .build()
                    );
      }


      // ================================================================
      // 500 - INTERNAL SERVER ERROR (Fallback / Safety Net)
      // Triggered when:
      // - NullPointerException
      // - Database crash
      // - Unexpected runtime error
      // - Any unhandled exception
      // You do NOT manually throw this normally
      // ================================================================
      @ExceptionHandler(Exception.class)
      public ResponseEntity<ApiResponse<?>> handleGeneral(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .success(false)
                            .message(ex.getLocalizedMessage())
                            .data(null)
                            .build());
      }
}