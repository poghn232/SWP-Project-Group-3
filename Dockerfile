# Sử dụng image Java chính thức
FROM eclipse-temurin:17-jdk

# Tạo thư mục app
WORKDIR /app

# Copy file jar vào image
COPY target/*.jar app.jar

# Mở cổng (Render sẽ dùng biến PORT tự động)
EXPOSE 8080

# Lệnh chạy app
ENTRYPOINT ["java", "-jar", "app.jar"]