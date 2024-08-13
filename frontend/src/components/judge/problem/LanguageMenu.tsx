'use client'
import { DownOutlined } from "@ant-design/icons"
import { Button, Dropdown, MenuProps, Space } from "antd"
import { useState } from "react"

const languages = ["C++", "C", "Python", "Java", "C#", "Javascript"]

export default function LanguageMenu() {

    const [currentLanguage, setCurrentLanguage] = useState(languages[0])

    const onClick: MenuProps['onClick'] = ({ key }) => {
        setCurrentLanguage(key)
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
                    {currentLanguage}
                    <DownOutlined />
                </Space>
            </Button>
        </Dropdown>
    )
}