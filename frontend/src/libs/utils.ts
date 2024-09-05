export const stringToSlug = (str: string) => {
    // Chuyển tất cả các ký tự thành chữ thường
    str = str.toLowerCase();
 
    // Thay thế các ký tự đặc biệt tiếng Việt
    str = str.replace(/đ/g, 'd');
 
    // Loại bỏ dấu tiếng Việt
    str = str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
 
    // Thay thế các ký tự không phải chữ cái hoặc số bằng dấu gạch ngang
    str = str.replace(/[^a-z0-9\s-]/g, '');
 
    // Thay thế khoảng trắng hoặc dấu gạch ngang liên tiếp bằng một dấu gạch ngang
    str = str.trim().replace(/\s+/g, '-').replace(/-+/g, '-');
 
    return str;
}

export const isValidEmail = (email: string) => {
    // Biểu thức chính quy kiểm tra định dạng email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

export const formatDate = (isoString: string): string => {
    const date = new Date(isoString);
  
    const day = date.getDate().toString().padStart(2, '0'); // Đảm bảo 2 chữ số
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Tháng 0-11, nên cộng 1
    const year = date.getFullYear();
  
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
  
    return `${day}/${month}/${year} ${hours}:${minutes}`;
}


import { FileType } from "./types";

export const getBase64 = (file: FileType): Promise<string> => 
    new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result as string);
    reader.onerror = (error) => reject(error);
})