package com.example.demo.repository;

import com.example.demo.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    // Kiểm tra user có đặt item đó không
    @Query("SELECT CASE WHEN COUNT(od) > 0 THEN true ELSE false END " +
           "FROM OrderDetail od JOIN Order o ON od.orderId = o.orderId " +
           "WHERE o.customerId = :userId AND od.itemId = :itemId AND od.itemType = 'product'")
    boolean existsByUserIdAndItemId(@Param("userId") int userId, @Param("itemId") int itemId);

    // Kiểm tra user có đặt Combo đó không
    @Query("SELECT CASE WHEN COUNT(od) > 0 THEN true ELSE false END " +
           "FROM OrderDetail od JOIN Order o ON od.orderId = o.orderId " +
           "WHERE o.customerId = :userId AND od.itemId = :comboId AND od.itemType = 'combo'")
    boolean existsByUserIdAndComboId(@Param("userId") int userId, @Param("comboId") int comboId);
}
