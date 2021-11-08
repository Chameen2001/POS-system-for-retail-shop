package controller;

import bo.BOFactory;
import bo.custom.impl.IncomeReportBOImpl;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import dto.AnnuallyChartDTO;
import dto.DailyChartDTO;
import dto.MonthlyChartDTO;

import java.sql.SQLException;

public class IncomeReportFormController {

    public LineChart dailyChart;
    public LineChart monthChart;
    public LineChart annualChart;
    public AnchorPane incomeReportContext;
    public Label totalDaily;
    public Label totalMonthly;
    public Label totalAnnually;
    private AnchorPane anchorPane;
    private AnchorPane reportContect;

    private final IncomeReportBOImpl incomeReportBO = (IncomeReportBOImpl) BOFactory.getInstance().getBOImpl(BOFactory.BOType.INCOME_REPORT_BO);

    public void initialize(){


        try {
            loadDailyChart();
            loadMonthlyChart();
            loadAnnuallyChart();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }

    private void loadAnnuallyChart() throws SQLException, ClassNotFoundException {
        AnnuallyChartDTO annuallyChartDTO = incomeReportBO.getAnnuallyIncome();
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>("January", annuallyChartDTO.getMonth1()));
        series.getData().add(new XYChart.Data<>("February", annuallyChartDTO.getMonth2()));
        series.getData().add(new XYChart.Data<>("March", annuallyChartDTO.getMonth3()));
        series.getData().add(new XYChart.Data<>("April", annuallyChartDTO.getMonth4()));
        series.getData().add(new XYChart.Data<>("May", annuallyChartDTO.getMonth5()));
        series.getData().add(new XYChart.Data<>("June", annuallyChartDTO.getMonth6()));
        series.getData().add(new XYChart.Data<>("July", annuallyChartDTO.getMonth7()));
        series.getData().add(new XYChart.Data<>("August", annuallyChartDTO.getMonth8()));
        series.getData().add(new XYChart.Data<>("September", annuallyChartDTO.getMonth9()));
        series.getData().add(new XYChart.Data<>("October", annuallyChartDTO.getMonth10()));
        series.getData().add(new XYChart.Data<>("November", annuallyChartDTO.getMonth11()));
        series.getData().add(new XYChart.Data<>("December", annuallyChartDTO.getMonth12()));

        annualChart.getData().add(series);

        totalAnnually.setText(annuallyChartDTO.getTotal());

    }

    private void loadMonthlyChart() throws SQLException, ClassNotFoundException {
        MonthlyChartDTO monthlyChartData = incomeReportBO.getMonthlyIncome();

        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>("Week 1",monthlyChartData.getWeek1()));
        series.getData().add(new XYChart.Data<>("Week 2",monthlyChartData.getWeek2()));
        series.getData().add(new XYChart.Data<>("Week 3",monthlyChartData.getWeek3()));
        series.getData().add(new XYChart.Data<>("Week 4",monthlyChartData.getWeek4()));

        monthChart.getData().add(series);

        totalMonthly.setText(monthlyChartData.getTotal());

    }

    private void loadDailyChart() throws SQLException, ClassNotFoundException {
        DailyChartDTO dailyChartDTO = incomeReportBO.getDailyIncome();
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>("2H", dailyChartDTO.getHour2()));
        series.getData().add(new XYChart.Data<>("4H", dailyChartDTO.getHour4()));
        series.getData().add(new XYChart.Data<>("6H", dailyChartDTO.getHour6()));
        series.getData().add(new XYChart.Data<>("8H", dailyChartDTO.getHour8()));
        series.getData().add(new XYChart.Data<>("10H", dailyChartDTO.getHour10()));
        series.getData().add(new XYChart.Data<>("12H", dailyChartDTO.getHour12()));
        series.getData().add(new XYChart.Data<>("14H", dailyChartDTO.getHour14()));
        series.getData().add(new XYChart.Data<>("16H", dailyChartDTO.getHour16()));
        series.getData().add(new XYChart.Data<>("18H", dailyChartDTO.getHour18()));
        series.getData().add(new XYChart.Data<>("20H", dailyChartDTO.getHour20()));
        series.getData().add(new XYChart.Data<>("22H", dailyChartDTO.getHour22()));
        series.getData().add(new XYChart.Data<>("24H", dailyChartDTO.getHour24()));

        dailyChart.getData().add(series);

        totalDaily.setText(dailyChartDTO.getTotal());
    }

    public void backOnAction(ActionEvent event) {
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(reportContect);
    }

    public void setPane(AnchorPane anchorPane,AnchorPane reportContext){
        this.anchorPane=anchorPane;
        this.reportContect=reportContext;
    }

}
