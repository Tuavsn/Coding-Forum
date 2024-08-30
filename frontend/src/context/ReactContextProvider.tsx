export default function ContextProvider = ({
    children
}: {
    children: React.ReactNode
}) {
    return (
        <ContextProvider value={}>
            {children}
        </ContextProvider>
    )
}