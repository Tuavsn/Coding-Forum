export const formatDate = (isoString: string): string => {
    const date = new Date(isoString);
  
    const day = date.getDate().toString().padStart(2, '0'); // Đảm bảo 2 chữ số
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Tháng 0-11, nên cộng 1
    const year = date.getFullYear();
  
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
  
    return `${day}/${month}/${year} ${hours}:${minutes}`;
}