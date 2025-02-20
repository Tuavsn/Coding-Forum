'use client'
import { ProblemSubmissionLanguageType } from "@/libs/enum";
import { DownOutlined } from "@ant-design/icons"
import { Button, Dropdown, MenuProps, Space } from "antd"

const languages = [
    ProblemSubmissionLanguageType.C,
    ProblemSubmissionLanguageType.CPLUSPLUS,
    ProblemSubmissionLanguageType.CSHARP,
    ProblemSubmissionLanguageType.PYTHON,
    ProblemSubmissionLanguageType.JAVA,
    ProblemSubmissionLanguageType.JAVASCRIPT
]

interface LanguageMenuProps {
    language: string;
    setLanguage: (value: ProblemSubmissionLanguageType) => void;
}

export default function LanguageMenu({ language, setLanguage }: LanguageMenuProps) {

    const onClick: MenuProps['onClick'] = ({ key }) => {
        setLanguage(key as ProblemSubmissionLanguageType)
    };    

    return (
        <Dropdown 
            menu={{
                items: languages.map((language, index) => ({
                    key: language,
                    label: language
                })),
                onClick
            }}
            placement="bottomLeft"
            arrow
        >
            <Button size="large" onClick={(e) => e.preventDefault()}>
                <Space>
                    {language}
                    <DownOutlined />
                </Space>
            </Button>
        </Dropdown>
    )
}