package dto;

public class DailyChartDTO {
    private Double  hour2=0.0;
    private Double  hour4=0.0;
    private Double  hour6=0.0;
    private Double  hour8=0.0;
    private Double  hour10=0.0;
    private Double  hour12=0.0;
    private Double  hour14=0.0;
    private Double  hour16=0.0;
    private Double  hour18=0.0;
    private Double  hour20=0.0;
    private Double  hour22=0.0;
    private Double  hour24=0.0;

    public DailyChartDTO() {
    }

    public DailyChartDTO(Double hour2, Double hour4, Double hour6, Double hour8, Double hour10, Double hour12, Double hour14, Double hour16, Double hour18, Double hour20, Double hour22, Double hour24) {
        this.hour2 = hour2;
        this.hour4 = hour4;
        this.hour6 = hour6;
        this.hour8 = hour8;
        this.hour10 = hour10;
        this.hour12 = hour12;
        this.hour14 = hour14;
        this.hour16 = hour16;
        this.hour18 = hour18;
        this.hour20 = hour20;
        this.hour22 = hour22;
        this.hour24 = hour24;
    }

    public Double getHour2() {
        return hour2;
    }

    public void setHour2(Double hour2) {
        this.hour2 = hour2;
    }

    public Double getHour4() {
        return hour4;
    }

    public void setHour4(Double hour4) {
        this.hour4 = hour4;
    }

    public Double getHour6() {
        return hour6;
    }

    public void setHour6(Double hour6) {
        this.hour6 = hour6;
    }

    public Double getHour8() {
        return hour8;
    }

    public void setHour8(Double hour8) {
        this.hour8 = hour8;
    }

    public Double getHour10() {
        return hour10;
    }

    public void setHour10(Double hour10) {
        this.hour10 = hour10;
    }

    public Double getHour12() {
        return hour12;
    }

    public void setHour12(Double hour12) {
        this.hour12 = hour12;
    }

    public Double getHour14() {
        return hour14;
    }

    public void setHour14(Double hour14) {
        this.hour14 = hour14;
    }

    public Double getHour16() {
        return hour16;
    }

    public void setHour16(Double hour16) {
        this.hour16 = hour16;
    }

    public Double getHour18() {
        return hour18;
    }

    public void setHour18(Double hour18) {
        this.hour18 = hour18;
    }

    public Double getHour20() {
        return hour20;
    }

    public void setHour20(Double hour20) {
        this.hour20 = hour20;
    }

    public Double getHour22() {
        return hour22;
    }

    public void setHour22(Double hour22) {
        this.hour22 = hour22;
    }

    public Double getHour24() {
        return hour24;
    }

    public void setHour24(Double hour24) {
        this.hour24 = hour24;
    }

    public String getTotal(){
        return String.valueOf(hour2+hour4+hour6+hour8+hour10+hour12+hour14+hour16+hour18+hour20+hour22+hour24);
    }

}
