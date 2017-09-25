package com.thelittlegym.mobile.handle;

import com.thelittlegym.mobile.entity.Result;
import com.thelittlegym.mobile.exception.MyException;
import com.thelittlegym.mobile.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Tony on 2017/9/13.
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public Result myHandle(MyException e){
      return ResultUtil.error(e.getCode(),e.getMessage());
    }


//    @ExceptionHandler({ RuntimeException.class })
//    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
//    public ModelAndView processException1(RuntimeException exception) {
//        log.info("自定义异常处理-RuntimeException");
//        ModelAndView m = new ModelAndView();
//        m.addObject("exception", exception.getMessage());
//        m.setViewName("error/err");
//        return m;
//
//    }
//
//    @ExceptionHandler({ Exception.class })
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ModelAndView processException2(Exception exception) {
//        log.info("自定义异常处理-RuntimeException");
//        ModelAndView m = new ModelAndView();
//        m.addObject("exception", exception.getMessage());
//        m.setViewName("error/err");
//        return m;
//
//    }
////    public static final String DEFAULT_ERROR_VIEW = "test.html";
////
////    @ExceptionHandler(value = NoHandlerFoundException.class)
////    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
////        ModelAndView mav = new ModelAndView();
////        mav.addObject("exception", e);
////        mav.addObject("url", req.getRequestURL());
////        mav.setViewName(DEFAULT_ERROR_VIEW);
////        return mav;
////    }

}

