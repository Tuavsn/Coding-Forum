import { Flex, Spin } from "antd"

export default function Loading() {
    return (
        <Flex align="center" gap="middle">
            <Spin fullscreen tip="Loading" spinning={false} size="large" />
        </Flex>
    )
}