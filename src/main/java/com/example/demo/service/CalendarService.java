package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

@Service
public class CalendarService {

    LocalDate now = LocalDate.now();
    int today = now.getDayOfMonth();
    int year = now.getYear();
    int month = now.getMonthValue();

    public String[][][] getMonthlyCalendar() {

        String[][][] calendarOutput = new String[3][6][7];

        Calendar calendar = Calendar.getInstance();

        //display 3 months from now
        for (int monthCount = 0; monthCount < 3; monthCount++) {

            if (month + monthCount > 12) {
                calendar.set(year + 1, month + monthCount - 12, 1);
            } else {
                calendar.set(year, month + monthCount, 1);
            }

            LocalDate firstDayOfMonth = LocalDate.of(year, month + monthCount, 1);
            DayOfWeek firstDayOfFirstWeek = firstDayOfMonth.getDayOfWeek();

            int daysInMonth = firstDayOfMonth.lengthOfMonth();
            int firstDay = firstDayOfFirstWeek.getValue();

            int week = 0;

            //create space before the first day start
            for (int day = 1; day < firstDay + 1; day++) {
                calendarOutput[monthCount][week][day - 1] = "";
            }

            int dayPosition = firstDay;

            for (int day = 1; day < daysInMonth + 1; day++) {

                //at least 1 week, need for preparation
                if (monthCount == 0 && day < today + 8) {
                    calendarOutput[monthCount][week][dayPosition] = "X";
                }

                //get day value
                else {
                    calendarOutput[monthCount][week][dayPosition] = String.valueOf(day);
                }

                //check if week passed
                if ((day + firstDay) % 7 == 0) {
                    week++;
                    dayPosition = 0;
                    continue;
                } else {
                    dayPosition++;
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
