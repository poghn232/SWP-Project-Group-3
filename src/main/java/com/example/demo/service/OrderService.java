package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TableOrderDetailsRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.SliderUI;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

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

    public Order findDraftOrderByUsername(String username) {
        List<Order> orders = orderRepository.findAllByCustomerName(username);
        for (Order order : orders) {
            if (order.getStatus().equals("DRAFT")) {
                return order;
            }
        }
        return null;
    }

    public Order findPendingOrderByUsername(String username) {
        List<Order> orders = orderRepository.findAllByCustomerName(username);
        orders.sort(Comparator.comparing(Order::getOrderDate).reversed());
        for (Order order : orders) {
            if (order.getStatus().equals("PENDING")) {
                return order;
            }
        }
        return null;
    }

    @Transactional
    public void createNewOrder(Integer partyId, String notes, String username, String phone, @AuthenticationPrincipal User user) {
        Party partyOrder = partyService.findById(partyId);

        double totalPrice = 0;
        for (Item item: partyOrder.getItems()) {
            totalPrice += item.getItemPrice();
        }

        Order newOrder = new Order();
        newOrder.setPartyId(partyId);
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
        }

        currentOrder.setStatus("PENDING");
        //total price of order
        currentOrder.setTotalPrice(currentOrder.getTotalPrice() * selectedTables.size());

        orderRepository.save(currentOrder);

        tableOrderDetailsRepository.saveAll(orderDetailsFiltered);
    }
}
