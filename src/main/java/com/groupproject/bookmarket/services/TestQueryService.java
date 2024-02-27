package com.groupproject.bookmarket.services;

import java.time.LocalDate;

public interface TestQueryService {
    public void testQuerySelectBooksFromFirstDateOfMonthToNow();
    public void testQuerySelectSalesFromTo();
    public void testQuerySelectSalesFromFirstDateOfMonthToNow();
    public void testQuerySelectOrdersFromFirstDateOfMonthToNow();
}
