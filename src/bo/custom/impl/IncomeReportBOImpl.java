package bo.custom.impl;

import bo.custom.IncomeReportBO;
import dao.DAOFactory;
import dao.custom.impl.OrderDAOImpl;
import entity.Order;
import dto.AnnuallyChartDTO;
import dto.DailyChartDTO;
import dto.MonthlyChartDTO;

import java.sql.SQLException;

public class IncomeReportBOImpl implements IncomeReportBO {
    private final OrderDAOImpl orderDAO = (OrderDAOImpl) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ORDERDAO);

    @Override
    public DailyChartDTO getDailyIncome() throws SQLException, ClassNotFoundException {

        DailyChartDTO dailyChartDTO = new DailyChartDTO();

        for (Order data : orderDAO.getTodayOrderTimesAndCosts()) {
            int i = Integer.parseInt(data.getOrderTime().split(":")[0]);
            if (i >= 0 & i < 2) {
                dailyChartDTO.setHour2(dailyChartDTO.getHour2() + data.getCost());
            } else if (i >= 2 & i < 4) {
                dailyChartDTO.setHour4(dailyChartDTO.getHour4() + data.getCost());
            } else if (i >= 4 & i < 6) {
                dailyChartDTO.setHour6(dailyChartDTO.getHour6() + data.getCost());
            } else if (i >= 6 & i < 8) {
                dailyChartDTO.setHour8(dailyChartDTO.getHour8() + data.getCost());
            } else if (i >= 8 & i < 10) {
                dailyChartDTO.setHour10(dailyChartDTO.getHour10() + data.getCost());
            } else if (i >= 10 & i < 12) {
                dailyChartDTO.setHour12(dailyChartDTO.getHour12() + data.getCost());
            } else if (i >= 12 & i < 14) {
                dailyChartDTO.setHour14(dailyChartDTO.getHour14() + data.getCost());
            } else if (i >= 14 & i < 16) {
                dailyChartDTO.setHour16(dailyChartDTO.getHour16() + data.getCost());
            } else if (i >= 16 & i < 18) {
                dailyChartDTO.setHour18(dailyChartDTO.getHour18() + data.getCost());
            } else if (i >= 18 & i < 20) {
                dailyChartDTO.setHour20(dailyChartDTO.getHour20() + data.getCost());
            } else if (i >= 20 & i < 22) {
                dailyChartDTO.setHour22(dailyChartDTO.getHour22() + data.getCost());
            } else {
                dailyChartDTO.setHour24(dailyChartDTO.getHour24() + data.getCost());
            }
        }
        return dailyChartDTO;
    }

    @Override
    public MonthlyChartDTO getMonthlyIncome() throws SQLException, ClassNotFoundException {

        MonthlyChartDTO monthlyChartDTO = new MonthlyChartDTO();

        for (Order data : orderDAO.getMonthlyOrderDateAndCost()) {
            int i = Integer.parseInt(data.getOrderDate().split("-")[2]);

            if (i <= 7) {
                monthlyChartDTO.setWeek1(monthlyChartDTO.getWeek1() + data.getCost());
            } else if (i > 7 & i <= 14) {
                monthlyChartDTO.setWeek2(monthlyChartDTO.getWeek2() + data.getCost());
            } else if (i > 14 & i <= 21) {
                monthlyChartDTO.setWeek3(monthlyChartDTO.getWeek3() + data.getCost());
            } else if (i > 21) {
                monthlyChartDTO.setWeek4(monthlyChartDTO.getWeek4() + data.getCost());
            }
        }
        return monthlyChartDTO;
    }

    @Override
    public AnnuallyChartDTO getAnnuallyIncome() throws SQLException, ClassNotFoundException {
        AnnuallyChartDTO annuallyChartDTO = new AnnuallyChartDTO();

        for (Order data : orderDAO.getAnnuallyOrderDateAndCost()) {
            int i = Integer.parseInt(data.getOrderDate().split("-")[1]);
            switch (i) {
                case 1:
                    annuallyChartDTO.setMonth1(annuallyChartDTO.getMonth1() + data.getCost());
                    break;
                case 2:
                    annuallyChartDTO.setMonth2(annuallyChartDTO.getMonth2() + data.getCost());
                    break;
                case 3:
                    annuallyChartDTO.setMonth3(annuallyChartDTO.getMonth3() + data.getCost());
                    break;
                case 4:
                    annuallyChartDTO.setMonth4(annuallyChartDTO.getMonth4() + data.getCost());
                    break;
                case 5:
                    annuallyChartDTO.setMonth5(annuallyChartDTO.getMonth5() + data.getCost());
                    break;
                case 6:
                    annuallyChartDTO.setMonth6(annuallyChartDTO.getMonth6() + data.getCost());
                    break;
                case 7:
                    annuallyChartDTO.setMonth7(annuallyChartDTO.getMonth7() + data.getCost());
                    break;
                case 8:
                    annuallyChartDTO.setMonth8(annuallyChartDTO.getMonth8() + data.getCost());
                    break;
                case 9:
                    annuallyChartDTO.setMonth9(annuallyChartDTO.getMonth9() + data.getCost());
                    break;
                case 10:
                    annuallyChartDTO.setMonth10(annuallyChartDTO.getMonth10() + data.getCost());
                    break;
                case 11:
                    annuallyChartDTO.setMonth11(annuallyChartDTO.getMonth11() + data.getCost());
                    break;
                case 12:
                    annuallyChartDTO.setMonth12(annuallyChartDTO.getMonth12() + data.getCost());
                    break;
            }
        }

        return annuallyChartDTO;
    }
}
