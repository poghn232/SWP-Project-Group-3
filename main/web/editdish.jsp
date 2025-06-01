<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Sửa món ăn</title></head>
<body>
    <h2>Sửa món ăn</h2>
    <form method="post" action="edit">
        <input type="hidden" name="itemId" value="${item.itemId}" />
        Tên: <input type="text" name="name" value="${item.name}"/><br/>
        Mô tả: <input type="text" name="description" value="${item.description}"/><br/>
        Giá: <input type="text" name="price" value="${item.price}"/><br/>
        Loại: <input type="text" name="category" value="${item.category}"/><br/>
        Ảnh URL: <input type="text" name="imageUrl" value="${item.imageUrl}"/><br/>
        Còn hàng? <input type="checkbox" name="isAvailable" value="true" ${item.available ? "checked" : ""}/><br/>
        <input type="submit" value="Cập nhật"/>
    </form>
</body>
</html>
