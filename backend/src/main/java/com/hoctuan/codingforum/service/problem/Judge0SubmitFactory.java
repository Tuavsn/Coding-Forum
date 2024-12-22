package com.hoctuan.codingforum.service.problem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.constant.SubmitType;
import com.hoctuan.codingforum.exception.CustomException;
import com.hoctuan.codingforum.service.problem.impl.AsynchronousSubmitStrategy;
import com.hoctuan.codingforum.service.problem.impl.SynchronousSubmitStrategy;

@Component
public class Judge0SubmitFactory {
    @Autowired
    private AsynchronousSubmitStrategy asynchronousSubmitStrategy;

    @Autowired
    private SynchronousSubmitStrategy synchronousSubmitStrategy;

    public Judge0SubmitStrategy getSubmitStrategy(SubmitType type) {
        switch(type) {
            case ASYNCHRONOUS:
                return asynchronousSubmitStrategy;
            case SYNCHRONOUS:
                return synchronousSubmitStrategy;
            default:
                throw new CustomException("Unsupported submit type", HttpStatus.BAD_REQUEST.value());
        }
    }
}
