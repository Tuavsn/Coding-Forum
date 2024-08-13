'use client'
import { CloudServerOutlined, PlayCircleOutlined } from "@ant-design/icons";
import { Button, Card, Flex, MenuProps } from "antd";
import LanguageMenu from "./LanguageMenu";
import MonacoEditor from "./MonacoEditor";
import { useState } from "react";

interface User {
    username: string;
    avatar: string;
}

interface Problem {
    id: string;
    user: User;
    header: string;
    totalComment: string;
    description: string;
    createAt: string;
}

export default function ProblemDetail({problem}: {problem: Problem}) {
    const [runLoading, setRunLoading] = useState(false)
    const [submitLoading, setSubmitLoading] = useState(false)

    const handleSetRunLoading = () => {
        setRunLoading(true)

        setTimeout(() => {
            setRunLoading(false)
        }, 3000);
    }

    const handleSetSubmitLoading = () => {
        setSubmitLoading(true)

        setTimeout(() => {
            setSubmitLoading(false)
        }, 3000);
    }
    
    return (
        <>
            <Flex gap={6} justify="center" className="m-4">
                <Button size="large" type="primary" loading={runLoading} disabled={submitLoading} onClick={handleSetRunLoading}><PlayCircleOutlined /><p>Run</p></Button>
                <Button size="large" type="primary" loading={submitLoading} disabled={runLoading} onClick={handleSetSubmitLoading}><CloudServerOutlined /><p>Submit</p></Button>
                <LanguageMenu />
            </Flex>
            <Flex gap={6}>
                <div className="h-[800px] w-[40%] drop-shadow">
                    <Card title={`Bài tập: ${problem.header}`} className="h-full w-full rounded-none overflow-y-scroll scrollbar-thin" >
                        <p>{problem.description}</p>
                    </Card>
                </div>
                <div className="h-[800px] w-[60%] drop-shadow">
                    <Card title="Code Editor" className="h-full w-full rounded-none overflow-hidden" styles={{body: {height: "100%", padding: "24px 0"}}}>
                        <MonacoEditor />
                    </Card>
                </div>
            </Flex>
        </>
    )
}