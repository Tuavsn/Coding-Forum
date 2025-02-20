import { GithubFilled, FacebookFilled } from "@ant-design/icons"
import { Menu, MenuProps } from "antd"

type MenuItem = Required<MenuProps>['items'][number]

const items: MenuItem[] = [
    {
        label: (
            <a href="https://github.com/Tuavsn" target="_blank"><strong>Github</strong></a>
        ),
        key: "github",
        icon: <GithubFilled />
    },
    {
        label: (
            <a href="https://www.facebook.com/hoc.tuan3213/" target="_blank"><strong>Facebook</strong></a>
        ),
        key: "facebook",
        icon: <FacebookFilled />
    },
]

export default function CustomFooter() {
    return (
        <Menu selectedKeys={[""]} className="justify-center" mode="horizontal" items={items}/>
    )
}