package com.example.taskapp.exception;

import com.example.taskapp.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler
            = new GlobalExceptionHandler();

    @Test
    void bad_IllegalArgumentExceptionは400になる() {
        IllegalArgumentException exception
                = new IllegalArgumentException("入力値が不正です。");

        ResponseEntity<ErrorResponse> result
                = handler.bad(exception);

        assertEquals(400, result.getStatusCode().value());
        assertNotNull(result.getBody());

        assertEquals(
                "VALIDATION_ERROR",
                result.getBody().code()
        );

        assertEquals(
                "入力値が不正です。",
                result.getBody().message()
        );
    }

    @Test
    void notFound_NotFoundExceptionは404になる() {
        NotFoundException exception
                = new NotFoundException("タスクが見つかりません。");

        ResponseEntity<ErrorResponse> result
                = handler.notFound(exception);

        assertEquals(404, result.getStatusCode().value());
        assertNotNull(result.getBody());

        assertEquals(
                "NOT_FOUND",
                result.getBody().code()
        );

        assertEquals(
                "タスクが見つかりません。",
                result.getBody().message()
        );
    }

    @Test
    void other_想定外例外は500になる() {
        Exception exception
                = new Exception("予期しないエラー");

        ResponseEntity<ErrorResponse> result
                = handler.other(exception);

        assertEquals(500, result.getStatusCode().value());
        assertNotNull(result.getBody());

        assertEquals(
                "INTERNAL_ERROR",
                result.getBody().code()
        );
    }
}
