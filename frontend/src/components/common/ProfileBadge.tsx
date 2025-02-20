interface Props {
    text: string;
}

export default function ProfileBadge(props: Props) {

    const {text} = props;

    const getBagdeColor = (text: string) => {
        switch(text) {
            case 'sys_admin': return 'bg-[#12459C]';
            case 'user': return 'bg-[#2F855A]'; // green-400
            case 'beginner': return 'bg-[#2F855A]'; // green-600
            case 'intermediate': return 'bg-[#D69E2E]'; // orange-600
            case 'expert': return 'bg-[#E53E3E]'; // red-600
            default: return 'bg-[#A0AEC0]'; // gray-500            
        }
    }

    return (
        <p className={`px-1 py-[1.2px] text-xs ${getBagdeColor(text)} rounded-md text-white font-bold`}>
            {text}
        </p>
    )
}