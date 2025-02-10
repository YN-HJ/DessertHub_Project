package com.desserthub.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.naming.ServiceUnavailableException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404 エラー処理 (Page Not Found)
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404 エラー処理
    public String handle404Error(Model model) {
        model.addAttribute("errorCode", 404);
        model.addAttribute("errorMessage", "Page not found!");
        return "error/404";  // 404.html ページへリダイレクト
    }

    // 500 エラー処理 (Internal Server Error)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 エラー処理
    public String handle500Error(Exception ex, Model model) {
        model.addAttribute("errorCode", 500);
        model.addAttribute("errorMessage", "Internal Server Error!");
        model.addAttribute("exception", ex.getMessage());  // 例外メッセージ
        return "error/500";  // 500.html ページへリダイレクト
    }

    // 503 エラー処理 (Service Unavailable)
    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE) // 503 エラー処理
    public String handle503Error(ServiceUnavailableException ex, Model model) {
        model.addAttribute("errorCode", 503);
        model.addAttribute("errorMessage", "Service is temporarily unavailable. Please try again later.");
        model.addAttribute("exception", ex.getMessage());  // 例外メッセージ
        return "error/503";  // 503.html ページへリダイレクト
    }    

    // 全てのビューから使用されるモデルの属性を追加
    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("appName", "My Application");
    }
}

