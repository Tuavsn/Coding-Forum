import Link from "next/link";
import React from "react";

interface MenuItem {
    label: string;
    labelSize?: 'small' | 'medium' | 'large';
    labelStyle?: string;
    key: string;
    href?: string | '#';
    icon?: React.ReactNode;
    iconSize?: 'small' | 'medium' | 'large';
    iconStyle?: string;
}

interface Props {
    items: MenuItem[];
    MenuStyle?: string;
}

export default function HeaderMenu(props: Props) {

    const {
        MenuStyle,
        items
    } = props;

    const MenuItems = (items: MenuItem[]) => {
        return items.map((item) => (
            <Link
                key={item.key}
                href={item.href??'#'}
                className="text-gray-950 hover:text-blue-500 transition-colors flex items-center gap-3"
            >
                {
                    item.icon && React.isValidElement(item.icon)
                        && React.cloneElement(item.icon as React.ReactElement, {
                            className: `${item.iconStyle} ${item.iconSize ? 
                                item.iconSize === 'small' 
                                ? 'text-xs' : item.iconSize === 'medium' 
                                ? 'text-base' : 'text-lg' 
                                : 'text-base'}`
                        })
                }
                <span className={`${item.labelStyle} ${item.labelSize ? 
                    item.labelSize === 'small' 
                    ? 'text-xs' : item.labelSize === 'medium' 
                    ? 'text-base' : 'text-lg' 
                    : 'text-base'}`}>{item.label}</span>
            </Link>
        ))
    }

    return (
        <nav className={`${MenuStyle} flex items-center space-x-6`}>
            {MenuItems(items)}
        </nav>
    )
}