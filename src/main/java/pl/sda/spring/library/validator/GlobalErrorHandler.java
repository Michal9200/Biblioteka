package pl.sda.spring.library.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.UUID;

@ControllerAdvice
@ResponseBody
public class GlobalErrorHandler {

        @ResponseBody
        @ExceptionHandler(value = AlreadyExistException.class)
        public ResponseEntity<Error> handleAlreadyExist(AlreadyExistException ex) {
            String errorCode = UUID.randomUUID().toString();
            System.out.println("Error code " + errorCode);
            ex.printStackTrace();

            return ResponseEntity.status(409).body(new Error(ex.getMessage(), LocalDateTime.now(), errorCode));
        }

        @ResponseBody
        @ExceptionHandler(value = RuntimeException.class)
        public ResponseEntity<Error> handleAnyRuntimeException(RuntimeException ex) {
            String errorCode = UUID.randomUUID().toString();
            System.out.println("Error code " + errorCode);
            ex.printStackTrace();

            return ResponseEntity.status(500).body(new Error(ex.getMessage(), LocalDateTime.now(), errorCode));
        }

        @ExceptionHandler(value = AccessDeniedException.class)
        public String handleAccessDenied() {
            return "redirect:/lib/login";
        }

        @AllArgsConstructor
        @Getter
        static class Error {
            private final String message;
            private final LocalDateTime errorTime;
            private final String errorCode;
        }
}
