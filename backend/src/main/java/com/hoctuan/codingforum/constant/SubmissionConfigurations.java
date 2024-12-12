package com.hoctuan.codingforum.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public final class SubmissionConfigurations {
    // Configuration settings
    @Value("${judge0.max_cpu_time_limit:15}")
    public static float MAX_CPU_TIME_LIMIT;
    @Value("${judge0.max_cpu_extra_time:2}")
    public static float MAX_CPU_EXTRA_TIME;
    @Value("${judge0.max_wall_time_limit:20}")
    public static float MAX_WALL_TIME_LIMIT;
    @Value("${judge0.max_memory_limit:256000}")
    public static float MAX_MEMORY_LIMIT;
    @Value("${judge0.max_stack_limit:128000}")
    public static float MAX_STACK_LIMIT;
    @Value("${judge0.max_max_processes_and_or_threads:120}")
    public static float MAX_MAX_PROCESSES_AND_OR_THREADS;
    @Value("${judge0.allow_enable_per_process_and_thread_time_limit:true}")
    public static float ALLOW_ENABLE_PER_PROCESS_AND_THREAD_TIME_LIMIT;
    @Value("${judge0.allow_enable_per_process_and_thread_memory_limit:true}")
    public static float ALLOW_ENABLE_PER_PROCESS_AND_THREAD_MEMORY_LIMIT;
    @Value("${judge0.max_max_file_size:4096}")
    public static float MAX_MAX_FILE_SIZE;
    @Value("${judge0.max_number_of_runs:20}")
    public static float MAX_NUMBER_OF_RUNS;
    // Param names
    public static final String SOURCE_CODE = "source_code"; // text
    public static final String LANGUAGE_ID = "language_id"; // integer
    public static final String COMPILER_OPTIONS = "compiler_options"; // string (max. 512 chars)
    public static final String COMMAND_LINE_ARGUMENTS = "command_line_arguments"; // string (max. 512 chars)
    public static final String STDIN = "stdin"; // text
    public static final String EXPECTED_OUTPUT = "expected_output"; // text
    public static final String CPU_TIME_LIMIT = "cpu_time_limit"; // float
    public static final String CPU_EXTRA_TIME = "cpu_extra_time"; // float
    public static final String WALL_TIME_LIMIT = "wall_time_limit"; // float
    public static final String MEMORY_LIMIT = "memory_limit"; // float
    public static final String STACK_LIMIT = "stack_limit"; // integer
    public static final String MAX_PROCESSES_AND_OR_THREADS = "max_processes_and_or_threads"; // integer
    public static final String ENABLE_PER_PROCESS_AND_THREAD_TIME_LIMIT = "enable_per_process_and_thread_time_limit"; // boolean
    public static final String ENABLE_PER_PROCESS_AND_THREAD_MEMORY_LIMIT = "enable_per_process_and_thread_memory_limit"; // boolean
    public static final String MAX_FILE_SIZE = "max_file_size"; // integer
    public static final String REDIRECT_STDERR_TO_STDOUT = "redirect_stderr_to_stdout"; // boolean
    public static final String ENABLE_NETWORK = "enable_network"; // boolean
    public static final String NUMBER_OF_RUNS = "number_of_runs"; // integer
    public static final String ADDITIONAL_FILES = "additional_files"; // Base64 Encoded String
    public static final String CALLBACK_URL = "callback_url"; // string
    public static final String STDOUT = "stdout"; // text
    public static final String STDERR = "stderr"; // text
    public static final String COMPILE_OUTPUT = "compile_output"; // text
    public static final String MESSAGE = "message"; // text
    public static final String EXIT_CODE = "exit_code"; // integer
    public static final String EXIT_SIGNAL = "exit_signal"; // integer
    public static final String STATUS = "status"; // object
    public static final String CREATED_AT = "created_at"; // datetime
    public static final String FINISHED_AT = "finished_at"; // datetime
    public static final String TOKEN = "token"; // string
    public static final String TIME = "time"; // float
    public static final String WALL_TIME = "wall_time"; // float
    public static final String MEMORY = "memory"; // float
    public static final String RESULT_WAIT = "wait"; // boolean
    public static final String ENCODED = "base64_encoded"; // boolean
    public static final String RETURN_FIELD = "field"; //string
    public static final String PAGE = "page"; //string
    public static final String PER_PAGE = "per_page"; //string
}