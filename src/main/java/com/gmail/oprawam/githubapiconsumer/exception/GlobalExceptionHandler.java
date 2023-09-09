//package com.gmail.oprawam.githubapiconsumer.exception;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//
////@RestControllerAdvice
////@Controller
//@ControllerAdvice
//
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class GlobalExceptionHandler
////        extends ResponseEntityExceptionHandler
//{
//    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
////    @ResponseStatus(HttpStatus.NOT_FOUND)
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<Map<Object, String>> handle(NotFoundException notFoundException) {
//        log.error(notFoundException.getMessage());
//        Map<Object, String> props = new HashMap<>();
//        String statusCode = String.valueOf(HttpStatus.NOT_FOUND.value());
//        props.put("status", statusCode);
//        props.put("Message", notFoundException.getMessage());
//        return new ResponseEntity<Map<Object, String>>(props, HttpStatus.NOT_FOUND);
//    }
//
////    @Override
////    protected Mono<ResponseEntity<Object>> handleNotAcceptableStatusException(NotAcceptableStatusException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
////        System.out.println("i am in");
////        return super.handleNotAcceptableStatusException(ex, headers, status, exchange);
////    }
//
//
////            @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
////    @ExceptionHandler({NotAcceptableException.class, NotAcceptableStatusException.class})
//
////    @ResponseStatus(code=HttpStatus.NOT_ACCEPTABLE, reason="Wrong Accept header")
////    @ExceptionHandler(NotAcceptableException.class)
////    public ResponseEntity<Map<Object, String>> handleNotAcceptable(NotAcceptableException notAcceptableException) {
////        log.error(notAcceptableException.getMessage());
////
////        Map<Object, String> props = new HashMap<>();
////        String statusCode = String.valueOf(HttpStatus.NOT_ACCEPTABLE.value());
////        props.put("status", statusCode);
////        props.put("Message", notAcceptableException.getMessage());
////        return new ResponseEntity<Map<Object, String>>(props, HttpStatus.NOT_ACCEPTABLE);
////    }
//
////    @ExceptionHandler(NotAcceptableException.class)
////    public ResponseStatusException handleNotAcceptable(NotAcceptableException notAcceptableException) {
////        log.error(notAcceptableException.getMessage());
////
////        Map<Object, String> props = new HashMap<>();
////        String statusCode = String.valueOf(HttpStatus.NOT_ACCEPTABLE.value());
////        props.put("status", statusCode);
////        props.put("Message", notAcceptableException.getMessage());
////        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, notAcceptableException.getMessage() + "nowy reason notacceptable");
////    }
//
////    @Nonnull
////    @Override
////    protected Mono<ResponseEntity<Object>> handleNotAcceptableStatusException(@Nonnull NotAcceptableStatusException ex, @Nonnull HttpHeaders headers,@Nonnull HttpStatusCode status,@Nonnull  ServerWebExchange exchange) {
////        log.error("jestem w handleNotAcceptableStatusException jooo");
////        log.error(ex.getMessage());
////        Map<Object, String> body = new HashMap<>();
////        String statusCode = String.valueOf(HttpStatus.NOT_ACCEPTABLE.value());
////        body.put("status", statusCode);
////        body.put("Message", ex.getMessage());
////        return Mono.just(new ResponseEntity<>(body, headers, HttpStatus.NOT_ACCEPTABLE));
////    }
//
////    @ExceptionHandler(GeneralResponseException.class)
////    public ResponseEntity<Map<Object, String>> handle(GeneralResponseException generalResponseException) {
////        return getUniversalMapResponseEntity(generalResponseException);
////    }
//
////    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
////    @ExceptionHandler(Exception.class)
////    public ResponseEntity<Map<Object, String>> handle(Exception exception) {
////        return getUniversalMapResponseEntity(exception);
////    }
//
//    private ResponseEntity<Map<Object, String>> getUniversalMapResponseEntity(Exception exception) {
//        log.error(exception.getMessage());
//        Map<Object, String> props = new HashMap<>();
//        String statusCode = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        props.put("status", statusCode);
//        props.put("Message", exception.getMessage());
//        return new ResponseEntity<Map<Object, String>>(props, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//}
//
