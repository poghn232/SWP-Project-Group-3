<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Dish List</title>
</head>
<body>
    <h2>Danh sách món ăn</h2>
    <a href="add">Thêm món mới</a>
    <table border="1">
        <tr>
            <th>ID</th><th>Tên</th><th>Mô tả</th><th>Giá</th><th>Loại</th><th>Ảnh</th><th>Trạng thái</th><th>Hành động</th>
        </tr>
        <c:forEach var="item" items="${items}">
            <tr>
                <td>${item.itemId}</td>
                <td>${item.name}</td>
                <td>${item.description}</td>
                <td>${item.price}</td>
                <td>${item.category}</td>
                <td><img src="${item.imageUrl}" width="50"/></td>
                <td>${item.available ? "Còn" : "Hết"}</td>
                <td>
                    <a href="edit?id=${item.itemId}">Sửa</a> |
                    <a href="delete?id=${item.itemId}" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
