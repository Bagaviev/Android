package com.example.rentateamtask.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MainClass {

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("per_page")
    @Expose
    private int perPage;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("data")
    @Expose
    private List<UserData> data = null;

    @SerializedName("support")
    @Expose
    private Support support;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<UserData> getData() {
        return data;
    }

    public void setData(List<UserData> data) {
        this.data = data;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (UserData ud: getData())
            sb.append(ud.toString() + "\n");
        return sb.toString();
    }
}