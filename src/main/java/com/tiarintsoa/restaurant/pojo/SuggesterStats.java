package com.tiarintsoa.restaurant.pojo;

import java.math.BigDecimal;

public class SuggesterStats {

    private BigDecimal cumulative_income;
    private BigDecimal cumulative_commission;
    private int id_suggester;
    private String firstname;
    private String lastname;
    private BigDecimal restau_income;
    private BigDecimal total_commission;

    private BigDecimal valable_commission;
    public SuggesterStats(int id_suggester, String firstname, String lastname, BigDecimal total_commission) {
        this.id_suggester = id_suggester;
        this.firstname = firstname;
        this.lastname = lastname;
        this.total_commission = total_commission;
    }

    public SuggesterStats() {
    }

    public BigDecimal getCumulative_income() {
        return cumulative_income;
    }

    public void setCumulative_income(BigDecimal cumulative_income) {
        this.cumulative_income = cumulative_income;
    }

    public BigDecimal getCumulative_commission() {
        return cumulative_commission;
    }

    public void setCumulative_commission(BigDecimal cumulative_commission) {
        this.cumulative_commission = cumulative_commission;
    }

    public BigDecimal getRestau_income() {
        return restau_income;
    }

    public void setRestau_income(BigDecimal restau_income) {
        this.restau_income = restau_income;
    }

    public BigDecimal getValable_commission() {
        return valable_commission;
    }

    public void setValableCommission(BigDecimal valable_commission) {
        this.valable_commission = valable_commission;
    }

    public int getId_suggester() {
        return id_suggester;
    }

    public void setId_suggester(int id_suggester) {
        this.id_suggester = id_suggester;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public BigDecimal getTotalCommission() {
        return total_commission;
    }

    public void setTotal_commission(BigDecimal total_commission) {
        this.total_commission = total_commission;
    }
}
