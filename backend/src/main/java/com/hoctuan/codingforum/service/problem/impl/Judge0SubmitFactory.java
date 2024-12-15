package com.hoctuan.codingforum.service.problem.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.service.problem.SubmissionStrategy;

@Component
public class Judge0SubmitFactory {
    @Autowired
    private SynchronousJudge0Submission synchronousJudge0Submission;

    @Autowired
    private AsynchronousJudge0Submission asynchronousJudge0Submission;

    public SubmissionStrategy getSubmissionStrategy(boolean isWaitingForResult) {
        return isWaitingForResult ? synchronousJudge0Submission : asynchronousJudge0Submission;
    }
}
