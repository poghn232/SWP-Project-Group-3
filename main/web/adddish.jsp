<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Thêm món ăn</title></head>
<body>
    <h2>Thêm món ăn mới</h2>
    <form method="post" action="add">
        Tên: <input type="text" name="name" /><br/>
        Mô tả: <input type="text" name="description" /><br/>
        Giá: <input type="text" name="price" /><br/>
        Loại: <input type="text" name="category" /><br/>
        Ảnh URL: <input type="text" name="imageUrl" /><br/>
        Còn hàng? <input type="checkbox" name="isAvailable" value="true"/><br/>
        <input type="submit" value="Thêm món"/>
    </form>
</body>
</html>
