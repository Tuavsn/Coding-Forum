import { Divider } from "antd";
import React from "react";

interface Props {
    dataSource?: any[]; // Data array passed to render the items
    renderItem?: (item: any, index: number) => React.ReactNode;
    header?: React.ReactNode;
    footer?: React.ReactNode;
    split?: boolean;
    styles?: React.CSSProperties;
    className?: string;
    bordered?: boolean;
    extra?: React.ReactNode;
    itemLayout?: 'horizontal' | 'vertical';
}

export default function List(props: Props) {
    /**
     * Destructuring props
     */
    const { 
        dataSource, 
        renderItem, 
        header, 
        footer, 
        split, 
        styles, 
        className, 
        bordered, 
        extra, 
        itemLayout 
    } = props;

    return (
        <div className={`list-container ${className}`} style={styles}>
            {header && <div className="list-header mb-4">{header}</div>}
            <ul
                className={`list ${bordered ? '' : ''} ${itemLayout === 'horizontal' ? 'space-x-4' : 'space-y-4'}`}
            >
                {extra && <div className="list-extra mb-4">{extra}</div>}
                {dataSource && dataSource.map((item, index) => (
                    <>
                        <li
                            className="list-item"
                            key={item.id ?? index}
                        >
                            {renderItem ? renderItem(item, index) : null}
                        </li>
                        {split && index < dataSource.length - 1 && (
                            <Divider style={{ margin: '12px 0', borderColor: '#ddd' }} />
                        )}
                    </>
                ))}
            </ul>
            {footer && <div className="list-footer mt-4">{footer}</div>}
        </div>
    );
}
