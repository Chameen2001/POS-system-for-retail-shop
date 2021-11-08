package bo.custom;

import bo.SuperBO;
import dto.AnnuallyChartDTO;
import dto.DailyChartDTO;
import dto.MonthlyChartDTO;
import entity.Order;

import java.sql.SQLException;

public interface IncomeReportBO extends SuperBO {
    DailyChartDTO getDailyIncome() throws SQLException, ClassNotFoundException;

    MonthlyChartDTO getMonthlyIncome() throws SQLException, ClassNotFoundException;

    AnnuallyChartDTO getAnnuallyIncome() throws SQLException, ClassNotFoundException;
}
