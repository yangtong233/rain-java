//package org.rain.common.exception;
//
//import lombok.extern.slf4j.Slf4j;
//
//
///**
// *
// */
//@RestControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(BaseException.class)
//    public R<?> baseExceptionHandler(BaseException e) {
//        log.error(ExceptionUtil.stacktraceToString(e));
//        return R.error(e.getCode(), e.getMessage());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public R<?> exceptionHandler(Exception e) {
//        log.error(ExceptionUtil.stacktraceToString(e));
//        return R.error(Resp.OTHER_ERROR.code, e.getClass().getName() + " -> " + e.getMessage());
//    }
//
//    /**
//     * 记录异常日志
//     */
//    private void logging(Exception e) {
//        Threads.execute(() -> {
//            //TODO 记录异常日志
//        });
//    }
//}
