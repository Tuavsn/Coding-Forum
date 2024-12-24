'use client'
import { Editor, OnChange } from "@monaco-editor/react";

interface MonacoEditorProps {
    theme: string;
    language: string;
    code: string;
    setCode: (value: string) => void;
}


export default function MonacoEditor({ theme, language, code, setCode }: MonacoEditorProps) {
    const handleEditorChange: OnChange = (value, e) => {
        setCode(value || '')
    }
    
    return (
        <>
            <Editor
                value={code}
                language={language}
                theme={theme}
                width="100%"
                className="max-w-full"
                height="100%"
                options={{
                    fontSize: 15,
                    fontFamily: "Consolas",
                    wordWrap: "on",
                    "semanticHighlighting.enabled": "configuredByTheme",
                    lineNumbers: "on",
                    minimap: {enabled: false}
                }}
                onChange={handleEditorChange}
            />
        </>
    )
}