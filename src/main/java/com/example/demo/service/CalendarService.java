package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

@Service
public class CalendarService {

    LocalDate today = LocalDate.now();
    int year = today.getYear();
    int month = today.getMonthValue(); // 1 = Jan, 12 = Dec

    public String[][][] getMonthlyCalendar() {
        String[][][] calendarOutput = new String[3][5][7];

        Calendar calendar = Calendar.getInstance();

        for (int monthCount = 0; monthCount < 3; monthCount++) {

            calendar.set(year, month + monthCount, 1);

            int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int week = 0;
            //create space before the first day start
            for (int day = 1; day < firstDayOfWeek; day++) {
                calendarOutput[monthCount][week][day - 1] = "";
            }

            int dayPosition = firstDayOfWeek - 1;

            for (int day = 1; day < daysInMonth; day++) {
                calendarOutput[monthCount][week][dayPosition] = String.valueOf(day);

                dayPosition++;
                if ((day + firstDayOfWeek - 1) % 7 == 0) {
                    week++;
                    dayPosition = 0;
                }
            }
        }

        return calendarOutput;
    }

    public String[] getMonths() {
        String[] months = new String[3];
        for (int i = 0; i < 3; i++) {
            months[i] = Month.of(month + i).name();
        }
        return months;
    }
}
