import { Inter } from "next/font/google";
import "./globals.css";
import ReactQueryProvider from "../context/ReactQueryProvider";
import MyHeader from "@/components/common/header/Header";
import MyFooter from "@/components/common/footer/Footer";
import { ConfigProvider, Layout } from "antd";
import { Content, Footer, Header } from "antd/es/layout/layout";
import { AntdRegistry } from "@ant-design/nextjs-registry";

const inter = Inter({ subsets: ["vietnamese"] });

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" suppressHydrationWarning className="overflow-y-scroll scrollbar-thin">
      <body className={inter.className}>
        <AntdRegistry>
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
              <Layout className="min-h-screen pt-20">
                <Header className="bg-white fixed top-0 left-0 right-0 z-10">
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
        </AntdRegistry>
      </body>
    </html>
  );
}