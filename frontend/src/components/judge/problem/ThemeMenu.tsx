'use client'
import { DownOutlined } from "@ant-design/icons"
import { Button, Dropdown, MenuProps, Space } from "antd"

const themes = [
    "vs-light",
    "vs-dark",
]

interface ThemeMenuProps {
    editorTheme: string;
    onThemeChange: (value: string) => void;
}

export default function ThemeMenu({ editorTheme, onThemeChange }: ThemeMenuProps) {

    const onClick: MenuProps['onClick'] = ({ key }) => {
        onThemeChange(key as string)
    };    

    return (
        <Dropdown 
            menu={{
                items: themes.map((themes, index) => ({
                    key: themes,
                    label: themes
                })),
                onClick
            }}
            placement="bottomLeft"
            arrow
        >
            <Button size="large" onClick={(e) => e.preventDefault()}>
                <Space>
                    {editorTheme}
                    <DownOutlined />
                </Space>
            </Button>
        </Dropdown>
    )
}