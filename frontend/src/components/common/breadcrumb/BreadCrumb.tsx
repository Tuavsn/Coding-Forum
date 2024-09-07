'use client'

import { CodeOutlined, CopyOutlined, HomeOutlined, UserOutlined } from "@ant-design/icons";
import { Breadcrumb } from "antd"
import { useParams } from "next/navigation";

export default function BreadCrumb({type}: {type: string}) {
    const params = useParams()

    const slugArray = Array.isArray(params.slug) ? params.slug : [params.slug];

    const items = [
        {
            icon: <HomeOutlined />,
            path: "Trang chủ"
        }
    ]

    switch(type) {
        case "post":
            items.push({
                icon: <CopyOutlined />,
                path: "Post"
            })
            break
        case "problem":
            items.push({
                icon: <CodeOutlined />,
                path: "Problem"
            })
            break
        case "profile":
            items.push({
                icon: <UserOutlined />,
                path: "Profile"
            })
            break
        default:
            return
    }

    slugArray.map((item, index) => {
        items.push({
            icon: <></>,
            path: item
        }) 
    })

    return (
        <Breadcrumb
            className="mb-2"
            items={items.map((item, index) => ({
                title: (
                    <>
                        {item.icon}
                        <span>{item.path}</span>
                    </>
                )
            }))}
        />
    )
}