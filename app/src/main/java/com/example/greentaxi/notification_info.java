package com.example.greentaxi;

public class notification_info {

    // 푸쉬 알림 상태
    private  String status;

    // 출발 위치 위도,경도
    private  Double s_Lati;
    private  Double s_Logi;

    // 도착 위치 위도,경도
    private  Double d_Lati;
    private  Double d_Logi;

    // 현재 위치 위도, 경도
    private  Double c_Lati;
    private  Double c_Logi;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getS_Lati() {
        return s_Lati;
    }

    public void setS_Lati(Double s_Lati) {
        this.s_Lati = s_Lati;
    }

    public Double getS_Logi() {
        return s_Logi;
    }

    public void setS_Logi(Double s_Logi) {
        this.s_Logi = s_Logi;
    }

    public Double getD_Lati() {
        return d_Lati;
    }

    public void setD_Lati(Double d_Lati) {
        this.d_Lati = d_Lati;
    }

    public Double getD_Logi() {
        return d_Logi;
    }

    public void setD_Logi(Double d_Logi) {
        this.d_Logi = d_Logi;
    }

    public Double getC_Lati() {
        return c_Lati;
    }

    public void setC_Lati(Double c_Lati) {
        this.c_Lati = c_Lati;
    }

    public Double getC_Logi() {
        return c_Logi;
    }

    public void setC_Logi(Double c_Logi) {
        this.c_Logi = c_Logi;
    }




  public notification_info(){}

    public notification_info(String status,Double c_Lati,Double c_Logi, Double s_Lati, Double s_Logi, Double d_Lati, Double d_Logi){
        this.status = status;
        this.c_Lati = c_Lati;
        this.c_Logi = c_Logi;
        this.s_Lati = s_Lati;
        this.s_Logi = s_Logi;
        this.d_Lati = d_Lati;
        this.d_Logi = d_Logi;
    }
}

