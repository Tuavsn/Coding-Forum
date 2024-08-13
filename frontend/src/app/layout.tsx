import { Inter } from "next/font/google";
import "./globals.css";
import ReactQueryProvider from "./ReactQueryProvider";
import MyHeader from "@/components/common/header/Header";
import MyFooter from "@/components/common/footer/Footer";
import { ConfigProvider, Layout, theme } from "antd";
import { Content, Footer, Header } from "antd/es/layout/layout";
import Loading from "@/components/common/loading/Loading";

const inter = Inter({ subsets: ["vietnamese"] });

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" suppressHydrationWarning className="overflow-y-scroll scrollbar-thin">
      <body className={inter.className}>
        <ConfigProvider
            theme={{
                components: {
                    Carousel: {
                        arrowSize: 50,
                        arrowOffset: 15,
                    },
                },
            }}
        >
          <ReactQueryProvider>
            <Loading />
            <Layout className="min-h-screen">
              <Header className="bg-white">
                  <MyHeader />
              </Header>
              <Layout>
                <Content className="container mx-auto p-6">
                  {children}
                </Content>
              </Layout>
              <Footer className="p-0">
                <MyFooter />
              </Footer>
            </Layout>
          </ReactQueryProvider>
        </ConfigProvider>
      </body>
    </html>
  );
}