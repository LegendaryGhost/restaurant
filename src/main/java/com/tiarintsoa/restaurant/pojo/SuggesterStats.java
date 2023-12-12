package com.tiarintsoa.restaurant.pojo;

import java.math.BigDecimal;

public class SuggesterStats {

    private int id_suggester;
    private String firstname;
    private String lastname;
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
