package com.example.demo.service;

import com.example.demo.model.TableOrderDetails;
import com.example.demo.model.TableSlot;
import com.example.demo.model.TableStatus;
import com.example.demo.repository.TableOrderDetailsRepository;
import jakarta.persistence.EntityManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TableOrderDetailsService {

    private final TableOrderDetailsRepository tableOrderDetailsRepository;
    private final EntityManager entityManager;

    //constructor
    public TableOrderDetailsService (TableOrderDetailsRepository tableOrderDetailsRepository, EntityManager entityManager, CalendarService calendarService) {
        this.tableOrderDetailsRepository = tableOrderDetailsRepository;
        this.entityManager = entityManager;
    }

    //create new orders first time
    @Transactional
    public void manageOrders() {
        LocalDate firstOrderDay = getFirstDayOfMonth(LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        LocalDate lastOrderDay = getLastDayOfMonth(LocalDate.now().getYear(), LocalDate.now().getMonthValue() + 2);

        //create orders first time
        if (tableOrderDetailsRepository.count() == 0) {
            createOrdersBetween(firstOrderDay, lastOrderDay);
        }
    }

    //auto-run, 0sec 0min 2hours 1st everymonth everydayofweek
    //delete the orders last month and create new month orders
    //used for creating new month orders <---
    @Scheduled(cron = "0 0 2 1 * ?")
    @Transactional
    public void monthlyOrderMaintenance() {
        System.out.println("Monthly order maintenance running...");

        //delete last month orders
        List<TableOrderDetails> allOrderToDelete = findAllByMonth(LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 3);
        tableOrderDetailsRepository.deleteAll(allOrderToDelete);

        //add new empty orders

        LocalDate firstNewMonthOrderDay = getFirstDayOfMonth(LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        LocalDate lastNewMonthOrderDay = getLastDayOfMonth(LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        createOrdersBetween(firstNewMonthOrderDay, lastNewMonthOrderDay);

        System.out.println("Maintenance successfully!");
    }

    private void createOrdersBetween(LocalDate startDate, LocalDate endDate) {

        List<TableOrderDetails> batchToSave = new ArrayList<>();

        final int BATCH_SIZE = 500;

        //create new empty orders
        for (; !startDate.isAfter(endDate); startDate = startDate.plusDays(1)) {
            for (TableSlot slot : TableSlot.values()) {
                for (int i = 1; i < 37; i++) {
                    TableOrderDetails newOrder = new TableOrderDetails();
                    newOrder.setTableOrderId(UUID.randomUUID());
                    newOrder.setOrderDate(startDate);
                    newOrder.setSlot(slot);
                    newOrder.setTableStatus(TableStatus.AVAILABLE);
                    newOrder.setTableNumber(i);
                    batchToSave.add(newOrder);

                    if (batchToSave.size() % BATCH_SIZE == 0) {
                        tableOrderDetailsRepository.saveAll(batchToSave);

                        entityManager.flush();

                        entityManager.clear();

                        batchToSave.clear();
                    }
                }
            }
        }

        if (!batchToSave.isEmpty()) {
            tableOrderDetailsRepository.saveAll(batchToSave);

            entityManager.flush();

            entityManager.clear();
        }
    }
    private List<TableOrderDetails> findAllByMonth(int year, int month) {

        // Lấy ngày đầu tiên của tháng
        LocalDate startDateOfMonth = getFirstDayOfMonth(year, month);

        // Lấy ngày cuối cùng của tháng
        LocalDate endDateOfMonth = getLastDayOfMonth(year, month);

        // Sử dụng phương thức findAllByOrderDateBetween từ Repository
        return tableOrderDetailsRepository.findAllByOrderDateBetween(startDateOfMonth, endDateOfMonth);
    }

    private LocalDate getFirstDayOfMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.atDay(1);
    }

    private LocalDate getLastDayOfMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.atEndOfMonth();
    }
    //---------------------------------------->

    public List<TableOrderDetails> getDefaultDay() {
        return tableOrderDetailsRepository.findAllByOrderDate(LocalDate.now());
    }

    public List<TableOrderDetails> findAllOrderDetailsByDate(LocalDate date) {
        return tableOrderDetailsRepository.findAllByOrderDate(date);
    }

    //delete old order details, before 1 week from now
    @Transactional
    public void deleteOrdersDetailBefore(LocalDate date) {
        tableOrderDetailsRepository.deleteOrdersBefore(date);
    }
}
