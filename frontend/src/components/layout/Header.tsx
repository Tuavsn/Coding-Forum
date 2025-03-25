'use client'
import { Avatar, Button, Dropdown, Input, MenuProps, message, Spin } from "antd";
import { 
    HomeOutlined, 
    CodeOutlined, 
    AreaChartOutlined, 
    InfoCircleOutlined,
    SearchOutlined,
    UserOutlined,
    CaretDownOutlined,
    LogoutOutlined,
    PlusOutlined
} from "@ant-design/icons"
import { FloatButton } from "antd";
import { useContext, useEffect, useState } from "react";
import { usePathname } from "next/navigation";
import { getInfo, logout } from "@/libs/actions/user.actions";
import { AuthContext } from "@/context/AuthContextProvider";
import { useQuery } from "react-query";
import Logo from "../../assets/images/logo.png";
import Image from "next/image";
import HeaderMenu from "./HeaderMenu";
import Link from "next/link";
import ProfileBadge from "../common/ProfileBadge";

export default function CustomHeader() {
    const {auth, setAuth} = useContext(AuthContext)
    
    const {data, isLoading} = useQuery('getUserInfo', getInfo)

    const path = usePathname()

    const [currentKey, setCurrentKey] = useState('home')

    const handleLogout = async () => {
        await logout().then(() => {
            setAuth(null)
            message.success("Đăng xuất thành công")
        })
    }

    const handleSetCurrentKey:MenuProps['onClick'] = (e) => {
        setCurrentKey(e.key)
    }

    const items = [
        {
            label: 'Trang chủ',
            key: "home",
            href: '/home',
            icon: <HomeOutlined />
        },
        {
            label: 'Luyện Code',
            key: "problem",
            href: '/problem',
            icon: <CodeOutlined />
        },
        // {
        //     label: 'Cuộc thi',
        //     labelSize: 'medium',
        //     key: "contest",
        //     href: '/contest',
        //     icon: <StarOutlined />
        // },
        {
            label: 'Xếp hạng',
            key: "ranking",
            href: '/ranking',
            icon: <AreaChartOutlined />
        },
        // {
        //     label: 'Nhóm',
        //     labelSize: 'medium',
        //     key: "group",
        //     href: '/group',
        //     icon: <BankOutlined />
        // },
        {
            label: 'Thông tin',
            key: "about",
            href: '/about',
            icon: <InfoCircleOutlined />
        },
    ]

    const dropDownItems: MenuProps['items'] = [
        {
            key: 'profile',
            label: <Link href={`/profile/${auth?.id}`}><button className="p-2 text-base">Trang cá nhân</button></Link>,
            icon: <UserOutlined className="text-base" />,
        },
        {
            key: 'logout',
            label: <button className="p-2 text-base" onClick={handleLogout}>Đăng Xuất</button>,
            danger: true,
            icon: <LogoutOutlined className="text-base" />
        },
    ]

    useEffect(() => {
        setCurrentKey(path.split('/')[1])
    },[path])

    useEffect(() => {
        if(data) setAuth(data)
    }, [data, setAuth])

    return (
        <>
            <header className="bg-white shadow-md fixed top-0 right-0 left-0 w-full z-50">
                <div className="flex justify-between items-center p-2">
                    {/* Logo */}
                    <div className="flex items-center px-4 gap-10">
                        <Link href='/home'>
                            <Image src={Logo} alt="logo" width={120} />
                        </Link>
                        {/* Search bar */}
                        <div className="flex items-center">
                            <Input
                                placeholder="Tìm kiếm"
                                allowClear
                                size="large"
                                prefix={<SearchOutlined style={{opacity: .6, fontSize: '1.3rem'}} />}
                                className="w-full"
                            />
                        </div>
                        {/* Menu */}
                        <div className="flex items-center">
                            <HeaderMenu
                                items={items}
                            />
                        </div>
                    </div>
                    {/* Account */}
                    {isLoading ? <Spin size="default" className="mx-10" /> : auth ? (
                        <Dropdown menu={{ items: dropDownItems }}>
                            <div className="flex items-center gap-2 mx-6 cursor-pointer">
                                <Avatar size="large" src={auth.avatar} />
                                <div>
                                    <p className="text-lg font-bold text-gray-600">{auth.username}</p>
                                    <div className="flex items-center gap-1">
                                        <ProfileBadge text={`${auth.role.toLocaleLowerCase()}`} />
                                        <ProfileBadge text={`${auth.achievement.toLocaleLowerCase()}`} />
                                    </div>
                                </div>
                                <CaretDownOutlined className="text-gray-500" />
                            </div>
                        </Dropdown>
                    ) : (
                        <Link href="/login">
                            <Button
                                icon={<UserOutlined />}
                                size="large"
                                className="bg-[#12459C] text-white mx-6"
                            >
                                Đăng nhập
                            </Button>
                        </Link>
                    )}
                </div>
            </header>
        </>
    )
}