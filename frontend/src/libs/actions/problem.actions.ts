import { deleteData, getData, getPublicData, postData, putData } from "@/utils/FetchData"
import { Problem, ProblemSubmission, SubmissionResult } from "../types";
import { ProblemSubmissionLanguageType, ProblemType } from "../enum";

export const getProblem = async(): Promise<Problem[]> => {
    const result = await getPublicData('api/v1/problem');
    return result.Data;
}

export const getProblemDetail = async(problemId: string | null): Promise<Problem> => {
    const result = await getPublicData(`api/v1/problem/${problemId}`);
    return result.Data;
}

export const runSolution = async({ problemId, solution } : { problemId: string, solution: { code: string, languageType: ProblemSubmissionLanguageType } }): Promise<SubmissionResult> => {
    const result = await postData(`api/v1/problem/${problemId}/run?type=SYNCHRONOUS`, solution);
    return result.Data;
}

export const submitSolution = async({ problemId, solution } : { problemId: string, solution: { code: string, languageType: ProblemSubmissionLanguageType } }): Promise<ProblemSubmission> => {
    const result = await postData(`api/v1/problem/${problemId}/submit?type=SYNCHRONOUS`, solution);
    return result.Data;
}

export const getSubmission = async(problemId: string): Promise<ProblemSubmission[]> => {
    const result = await getData(`api/v1/problem/${problemId}/submissions?size=9999`)
    return result.Data.content;
}

export const getSubmissionDetail = async(submissionId: string): Promise<ProblemSubmission> => {
    const result = await getData(`api/v1/problem/${submissionId}`);
    return result.Data;
}

export const createProblem = async({ newProblem }: { newProblem: { title: string, description: string, example: string, tags: string, thumbnail: string, difficulty: ProblemType, testCases: string, totalScore: number } }) => {
    const result = await postData('api/v1/problem', newProblem);
    return result;
}

export const updateProblem = async({ problemId, newProblem }: { problemId: string, newProblem: { title: string, description: string, example: string, tags: string, thumbnail: string, difficulty: ProblemType, testCases: string, totalScore: number } }) => {
    const result = await putData(`api/v1/problem/${problemId}`, newProblem);
    return result;
}

export const deleteProblem = async(problemId: string) => {
    const result = await deleteData(`api/v1/problem/${problemId}`);
    return result;
}