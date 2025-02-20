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
                icon: <CopyOutlined className="text-base" />,
                path: "Post"
            })
            break
        case "problem":
            items.push({
                icon: <CodeOutlined className="text-base" />,
                path: "Problem"
            })
            break
        case "profile":
            items.push({
                icon: <UserOutlined className="text-base" />,
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
                        <span className="text-base">{item.path}</span>
                    </>
                )
            }))}
        />
    )
}