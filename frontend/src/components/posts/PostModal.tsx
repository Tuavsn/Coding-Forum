'use client'
import { Button, Col, Form, Input, Modal, Row, Select, Upload, UploadFile, UploadProps } from "antd";
import TextEditor from "../common/TextEditor";
import { getBase64 } from "@/libs/utils";
import { FileType, PostImage, Topic } from "@/libs/types";
import UploadButton from "../common/UploadButton";
import { getTopic } from "@/libs/actions/post.acttion";
import { useQuery } from "react-query";

interface PostContent {
    id: string;
    header: string;
    content: string;
    images: PostImage[];
    previewImages: string;
    imageFiles: UploadFile[];
    topics: Topic[];
}

interface PostModalProps {
    isOpen: boolean;
    postContent: PostContent;
    toggleAction: (type: 'create' | 'update' | 'close') => void;
    onChange: <K extends keyof PostContent> (field: K, value: PostContent[K]) => void;
    onUpdate?: () => void;
    onCreate?: () => void;
    isCreateLoading?: boolean;
    isUpdateLoading?: boolean;
}

export default function PostModal(props: PostModalProps) {

    const { data, isLoading } = useQuery<Topic[]>('getTopicsOptions', getTopic);

    const {
        isOpen,
        postContent,
        toggleAction,
        onChange,
        onCreate,
        onUpdate,
        isCreateLoading,
        isUpdateLoading
    } = props;

    const handlePreview = async (file: UploadFile) => {
        if (!file.url && !file.preview) {
            file.preview = await getBase64(file.originFileObj as FileType)
        }
    
        onChange('previewImages', file.url || (file.preview as string))
    }

    const handleImageUpload: UploadProps['onChange'] = async ({ fileList: newFileList }) => {
        const updatedFileList = await Promise.all(
            newFileList.map(async (file) => {
                if (file.originFileObj && !file.url && !file.preview) {
                const base64 = await getBase64(file.originFileObj as FileType)
                return {
                    ...file,
                    url: base64,
                }
                }
                return file
            })
        )

        onChange('imageFiles', updatedFileList)

        const postImages = updatedFileList.map((file) => ({
            image: file.url as string
        }))

        onChange('images', postImages);
    }

    return (
        <Modal
            open={isOpen}
            onCancel={() => toggleAction('close')}
            footer={postContent.id === '' ? [
                <Button key="cancel" onClick={() => toggleAction('close')}>
                    Hủy
                </Button>,
                <Button
                    key="update"
                    onClick={onCreate}
                    type="primary"
                    loading={isCreateLoading}
                >
                    Tạo post
                </Button>,
            ] : [
                <Button key="cancel" onClick={() => toggleAction('close')}>
                    Hủy
                </Button>,
                <Button
                    key="update"
                    onClick={onUpdate}
                    type="primary"
                    loading={isUpdateLoading}
                >
                    Cập nhật
                </Button>,
            ]}
            width={900}
            >
            <Form layout="vertical">
                <Row gutter={16}>
                <Col span={22}>
                    <Form.Item
                    label="Tiêu đề"
                    rules={[{ required: true, message: 'Nhập tiêu đề bài Post' }]}
                    >
                    <Input
                        style={{ width: '100%' }}
                        onChange={(e) => onChange('header', e.target.value)}
                        value={postContent.header}
                        placeholder="Nhập tiêu đề bài Post"
                    />
                    </Form.Item>
                </Col>
                </Row>
                <Row gutter={16}>
                <Col span={22}>
                    <Form.Item
                    label="Nội dung"
                    rules={[
                        {
                        required: true,
                        message: 'Nhập nội dung bài Post',
                        },
                    ]}
                    >
                    <TextEditor
                        data={postContent.content}
                        setData={(value: string) => onChange('content', value)}
                    />
                    </Form.Item>
                </Col>
                </Row>
                <Row gutter={16}>
                    <Col span={22}>
                        <Form.Item
                            label="Chủ đề"
                            rules={[{ required: true, message: 'Chọn ít nhất 1 chủ đề' }]}
                        >
                            <Select
                                mode="multiple"
                                allowClear
                                placeholder="Chọn chủ đề"
                                // Chuyển đổi mảng các id được chọn thành mảng các đối tượng Topic
                                onChange={(selectedIds: string[]) => {
                                    const selectedTopics = data?.filter((topic: Topic) =>
                                    selectedIds.includes(topic.id)
                                    );
                                    onChange('topics', selectedTopics);
                                }}
                                // Hiển thị giá trị hiện tại dưới dạng mảng các id của các Topic đã chọn
                                value={postContent.topics.map(topic => topic.id)}
                                options={data?.map((topic: Topic) => ({
                                    value: topic.id,
                                    label: topic.name
                                }))}
                            />
                            {/* <Select
                                mode="multiple"
                                allowClear
                                placeholder="Chọn chủ đề"
                                onChange={(value) => onChange('topics', value)}
                                value={postContent.topics}
                                options={data?.map(topic => ({
                                    value: topic.id,
                                    label: topic.name
                                }))}
                            /> */}
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={16}>
                <Col span={22}>
                    <Form.Item
                    label="Hình ảnh"
                    rules={[
                        {
                        required: true,
                        message: 'Upload ảnh bài viết',
                        },
                    ]}
                    >
                    <Upload
                        listType="picture-card"
                        fileList={postContent.imageFiles}
                        onPreview={handlePreview}
                        onChange={handleImageUpload}
                        beforeUpload={() => false}
                    >
                        {postContent.imageFiles.length >= 8 ? null : <UploadButton />}
                    </Upload>
                    {postContent.previewImages && <img src={postContent.previewImages} alt="preview" />}
                    </Form.Item>
                </Col>
                </Row>
            </Form>
        </Modal>
    )
}