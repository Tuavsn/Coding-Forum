export const uploadImage = async(formData: FormData) => {
    try {
        const response = await fetch(
          'https://api.cloudinary.com/v1_1/ds75yhrpy/image/upload',
          {
            method: 'POST',
            body: formData,
          }
        );
  
        const data = await response.json();
        return data.secure_url; // Nhận URL của ảnh sau khi upload thành công
      } catch (error) {
        console.error('Error uploading image:', error);
      }
}