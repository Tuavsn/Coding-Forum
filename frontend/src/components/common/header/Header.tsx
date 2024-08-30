'use client'
import { Badge, Menu, MenuProps, message } from "antd";
import { 
    HomeOutlined, 
    CodeOutlined, 
    AreaChartOutlined, 
    BankOutlined, 
    InfoCircleOutlined, 
    UserOutlined,
    BellOutlined,
    MessageOutlined,
    StarOutlined
} from "@ant-design/icons"
import Link from "next/link";
import { FloatButton } from "antd";
import { useEffect, useState } from "react";
import { usePathname } from "next/navigation";
import { logout } from "@/libs/actions/user.actions";
import { useQuery, useQueryClient } from "react-query";

type MenuItem = Required<MenuProps>['items'][number]

export default function Header() {
    const queryClient = useQueryClient()

    const path = usePathname()

    const [currentKey, setCurrentKey] = useState('home')

    const [openMessage, setOpenMessage] = useState<'default' | 'primary'>('default')

    const [openNotify, setOpenNotify] = useState<'default' | 'primary'>('default')

    const [username, setUsername] = useState<string | null>(sessionStorage.getItem('username'))

    useQuery({queryKey: ['getUsername'], queryFn: () => setUsername(sessionStorage.getItem('username'))})

    const handleLogout = async () => {
        await logout().then(() => {
            // setUsername(null)
            queryClient.invalidateQueries({queryKey: ['getUsername']})
            message.success("Đăng xuất thành công")
        })
    }

    const handleSetCurrentKey:MenuProps['onClick'] = (e) => {
        setCurrentKey(e.key)
    }

    const handleOpenMessage = () => {
        setOpenMessage(preType => (preType === 'default' ? 'primary' : 'default'))
    }

    const handleOpenNotify = () => {
        setOpenNotify(preType => (preType === 'default' ? 'primary' : 'default'))
    }

    const items: MenuItem[] = [
        {
            label: (
                <Link href="/home">Trang Chủ</Link>
            ),
            key: "home",
            icon: <HomeOutlined />
        },
        {
            label: (
                <Link href="/problem">Problem Solving</Link>
            ),
            key: "problem",
            icon: <CodeOutlined />
        },
        // {
        //     label: (
        //         <Link href="/contest">Cuộc Thi</Link>
        //     ),
        //     key: "contest",
        //     icon: <StarOutlined />
        // },
        {
            label: (
                <Link href="/ranking">Xếp Hạng</Link>
            ),
            key: "ranking",
            icon: <AreaChartOutlined />
        },
        // {
        //     label: (
        //         <Link href="/group">Nhóm</Link>
        //     ),
        //     key: "group",
        //     icon: <BankOutlined />
        // },
        {
            label: (
                <Link href="/about">Thông Tin</Link>
            ),
            key: "about",
            icon: <InfoCircleOutlined />
        },
        {
            label: username ? username : "Tài khoản",
            key: "account",
            icon: <UserOutlined />,
            children: username ? [
                { label: "Hồ Sơ", key: "profile" },
                { label: <button onClick={handleLogout}>Đăng Xuất</button>, key: "logout", danger: true}
            ] : [
                { label: <Link href="/login">Đăng Nhập</Link>, key: "login"}
            ]
        }
    ]

    useEffect(() => {
        setCurrentKey(path.split('/')[1])
    },[path])

    return (
        <>
            <Menu 
                className="justify-center" 
                selectedKeys={[currentKey]} 
                mode="horizontal" 
                items={items} 
                onClick={handleSetCurrentKey}
            />
            <FloatButton.Group shape="circle" style={{ insetInlineEnd: 24 }}>
                <Badge count={1000} overflowCount={99}>
                    <FloatButton 
                    icon={<MessageOutlined />} 
                    tooltip={<div>Tin Nhắn</div>} 
                    type={openMessage}
                    style={{ insetInlineEnd: 24 }}
                    onClick={handleOpenMessage}
                    />
                </Badge>
                <Badge count={1000} overflowCount={99}>
                    <FloatButton 
                    icon={<BellOutlined />} 
                    tooltip={<div>Thông Báo</div>} 
                    type={openNotify}
                    style={{ insetInlineEnd: 24 }}
                    onClick={handleOpenNotify}
                    />
                </Badge>
                <FloatButton.BackTop 
                    tooltip={<div>Trở về đầu trang</div>}
                    visibilityHeight={0} 
                />
            </FloatButton.Group>
        </>
    )
}