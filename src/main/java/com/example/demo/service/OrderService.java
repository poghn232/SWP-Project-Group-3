package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.model.Order;
import com.example.demo.model.Party;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

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

    @Transactional
    public void createNewOrder(Integer partyId, String notes, String username, String phone, @AuthenticationPrincipal User user) {
        Party partyOrder = partyService.findById(partyId);

        double totalPrice = 0;
        for (Item item: partyOrder.getItems()) {
            totalPrice += item.getItemPrice();
        }

        Order newOrder = new Order();
        newOrder.setTotalPrice(totalPrice);
        newOrder.setNotes(notes);
        newOrder.setCustomerName(username);
        newOrder.setCustomerPhone(phone);
        newOrder.setUser(user);

        orderRepository.save(newOrder);
    }
}
