package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TableOrderDetailsRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    TableOrderDetailsService tableOrderDetailsService;

    @Autowired
    TableOrderDetailsRepository tableOrderDetailsRepository;

    @Autowired
    private PartyService partyService;

    public List<Order> findAllPaidPendingOrders() {
        return orderRepository.findAllByStatusAndPaymentStatus("PENDING", "PAID");
    }

    public List<Order> findAllOrdersByUsername(@AuthenticationPrincipal User user) {
        return orderRepository.findAllByCustomerName(user.getUsername());
    }

    public Order findOrderByOrderId(UUID orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    public Order findDraftOrderByUsername(String username) {
        List<Order> orders = orderRepository.findAllByCustomerName(username);
        for (Order order : orders) {
            if (order.getStatus().equals("DRAFT")) {
                return order;
            }
        }
        return null;
    }

    public List<Order> findPendingOrdersByUsername(String username) {
        List<Order> pendingOrders = new ArrayList<>();
        for (Order order : orderRepository.findAllByCustomerName(username)) {
            if (order.getStatus().equals("PENDING")) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }

    //as a result of wrong status knowledge
    public List<Order> findUnpaidPendingOrdersByUsername(String username) {
        List<Order> pendingOrders = new ArrayList<>();
        for (Order order : orderRepository.findAllByCustomerName(username)) {
            if (order.getStatus().equals("PENDING") && order.getPaymentStatus().equals("UNPAID")) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }

    public Order findLatestPendingOrderByUsername(String username) {
        if (orderRepository.findAllByCustomerName(username).isEmpty()) {
            return null;
        }
        else {
            List<Order> pendingOrdersWithDescendingOrderDate = orderRepository.findAllByCustomerName(username).stream()
                    .filter(order -> "PENDING".equals(order.getStatus()))
                    .sorted(Comparator.comparing(Order::getOrderDate).reversed()).toList();
            return pendingOrdersWithDescendingOrderDate.get(0);
        }
    }

    public void deleteDraftOrder(String username) {
        orderRepository.delete(findDraftOrderByUsername(username));
    }

    @Transactional
    public void createNewOrder(Integer partyId, String notes, String username, String phone, @AuthenticationPrincipal User user) {
        Party partyOrder = partyService.findById(partyId);

        double totalPrice = 0;
        for (Item item : partyOrder.getItems()) {
            totalPrice += item.getItemPrice();
        }

        Order newOrder = new Order();
        newOrder.setPartyOrder(partyOrder);
        newOrder.setTotalPrice(totalPrice);
        newOrder.setNotes(notes);
        newOrder.setCustomerName(username);
        newOrder.setCustomerPhone(phone);
        newOrder.setUser(user);

        orderRepository.save(newOrder);
    }

    @Transactional
    public void addTablesToOrder(List<Integer> selectedTables, LocalDate selectedDate, String selectedTime, @AuthenticationPrincipal User user) {

        List<TableOrderDetails> orderDetailsFiltered = tableOrderDetailsService.filterTableOrders(selectedTables, selectedDate, selectedTime);
        Order currentOrder = findDraftOrderByUsername(user.getUsername());

        //set orderdetails from draft to pending, set detail order id to current user order id
        for (TableOrderDetails orderDetail : orderDetailsFiltered) {
            orderDetail.setOrder(currentOrder);
            orderDetail.setTableStatus(TableStatus.PENDING);
            currentOrder.setOrderDate(LocalDateTime.now());

            //set expiration date from the time user order table plus 5 minutes
            currentOrder.setExpirationDate(LocalDateTime.now().plusMinutes(5));
        }

        currentOrder.setStatus("PENDING");
        //total price of order
        currentOrder.setTotalPrice(currentOrder.getTotalPrice() * selectedTables.size());

        orderRepository.save(currentOrder);

        tableOrderDetailsRepository.saveAll(orderDetailsFiltered);
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void orderCleanupScheduler() {

        List<Order> cancelledOrderBatch = new ArrayList<>();
        for (Order pendingOrder : orderRepository.findAll()) {
            if (pendingOrder.getStatus().equals("PENDING") && pendingOrder.getExpirationDate() != null) {
                if (pendingOrder.getExpirationDate().isBefore(LocalDateTime.now())) {
                    pendingOrder.setStatus("CANCELLED");
                    cancelledOrderBatch.add(pendingOrder);
                }
            }
        }
        orderRepository.saveAll(cancelledOrderBatch);

        //set orders from PENDING to AVAILABLE if user didnt checkout
        // the scheduler will find order with expiration date past the current time real-timely, then change the order status
        List<Order> cancelledOrders = orderRepository.findAllByStatusAndExpirationDateBefore("CANCELLED", LocalDateTime.now());

        //change status, delete order
        for (Order cancelledOrder : cancelledOrders) {
            List<TableOrderDetails> newBatch = new ArrayList<>();
            for (TableOrderDetails cancelledOrderTable : cancelledOrder.getTableOrderDetails()) {

                //reset tables
                cancelledOrderTable.setTableStatus(TableStatus.AVAILABLE);
                cancelledOrderTable.setOrder(null);
                newBatch.add(cancelledOrderTable);
            }
            tableOrderDetailsRepository.saveAll(newBatch);
        }

        orderRepository.deleteAll(cancelledOrders);
    }


    //because of
    @Transactional
    public void successOrder(@AuthenticationPrincipal User user) {
        List<Order> pendingOrders = findPendingOrdersByUsername(user.getUsername());

        if (!pendingOrders.isEmpty()) {
            for (Order order : pendingOrders) {

                //set tables to be occupied
                for (TableOrderDetails tableOrderDetails : order.getTableOrderDetails()) {
                    tableOrderDetails.setTableStatus(TableStatus.OCCUPIED);
                }
                order.setExpirationDate(null);
                order.setPaymentStatus("PAID");
            }
        }
        orderRepository.saveAll(pendingOrders);
    }

    @Transactional
    public void updateOrderStatusByStaff(UUID orderId, String status) {
        Order originalOrder = findOrderByOrderId(orderId);

        originalOrder.setStatus(status);
        orderRepository.save(originalOrder);
    }
}
