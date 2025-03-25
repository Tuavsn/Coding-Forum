import { Button, Col, Form, Input, Modal, Row, Upload, UploadFile, UploadProps } from "antd";
import TextEditor from "../common/TextEditor";
import { getBase64 } from "@/libs/utils";
import { FileType, PostImage } from "@/libs/types";
import UploadButton from "../common/UploadButton";

interface PostContent {
    id: string;
    header: string;
    content: string;
    images: PostImage[];
    previewImages: string;
    imageFiles: UploadFile[];
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