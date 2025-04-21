import { Problem, ProblemSubmission, SubmissionResult } from "../constant/types";
import { ProblemSubmissionLanguageType, ProblemType } from "../constant/enum";
import { ApiUtil } from "../utils/apiUtil";
import { ApiEndPoint } from "../constant/constant";

export const ProblemService = {
    // Problem
    getProblem: async(): Promise<Problem[]> => {
        const result = await ApiUtil.getPublicData(ApiEndPoint.PROBLEM);
        return result.Data;
    },
    getProblemDetail: async(problemId: string | null): Promise<Problem> => {
        const result = await ApiUtil.getPublicData(`${ApiEndPoint.PROBLEM}/${problemId}`);
        return result.Data;
    },
    createProblem: async({ newProblem }: { newProblem:
        {
            title: string,
            description: string,
            example: string,
            tags: string,
            thumbnail: string,
            difficulty: ProblemType,
            testCases: string,
            totalScore: number 
        }
    }) => {
        const result = await ApiUtil.postData(`${ApiEndPoint.PROBLEM}`, newProblem);
        return result;
    },
    updateProblem: async({ problemId, newProblem }: { problemId: string, newProblem: 
        { 
            title: string, 
            description: string, 
            example: string, 
            tags: string, 
            thumbnail: string, 
            difficulty: ProblemType, 
            testCases: string, 
            totalScore: number 
        } 
    }) => {
        const result = await ApiUtil.putData(`${ApiEndPoint.PROBLEM}/${problemId}`, newProblem);
        return result;
    },
    deleteProblem: async(problemId: string) => {
        const result = await ApiUtil.deleteData(`${ApiEndPoint.PROBLEM}/${problemId}`);
        return result;
    },
    // Submission
    getSubmissions: async(problemId: string): Promise<ProblemSubmission[]> => {
        const result = await ApiUtil.getData(`${ApiEndPoint.PROBLEM}/${problemId}/submissions?size=9999`)
        return result.Data.content;
    },
    getSubmissionDetail: async(submissionId: string): Promise<ProblemSubmission> => {
        const result = await ApiUtil.getData(`${ApiEndPoint.PROBLEM}/${submissionId}`);
        return result.Data;
    },
    // Solution
    runSolution: async({ problemId, solution } : { problemId: string, solution: 
        { 
            code: string, 
            languageType: ProblemSubmissionLanguageType 
        } 
    }): Promise<SubmissionResult> => {
        const result = await ApiUtil.postData(`${ApiEndPoint.PROBLEM}/${problemId}/run?type=SYNCHRONOUS`, solution);
        return result.Data;
    },
    submitSolution: async({ problemId, solution } : { problemId: string, solution: 
        { 
            code: string, 
            languageType: ProblemSubmissionLanguageType 
        } 
    }): Promise<ProblemSubmission> => {
        const result = await ApiUtil.postData(`${ApiEndPoint.PROBLEM}/${problemId}/submit?type=SYNCHRONOUS`, solution);
        return result.Data;
    }
}