package com.hoctuan.codingforum.service.problem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.constant.ErrorCode;
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
                throw new CustomException(ErrorCode.UNSUPPORTED_SUBMIT_TYPE);
        }
    }
}
