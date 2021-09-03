package com.example.afishaandroidapp.dao;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    private static int COUNTER = 0;

    private int id;
    private Types type;
    private String name;
    private Date date;
    private int price;
    private String description;
    private int flagResImg;
    private boolean isBought;

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFlagResImg() {
        return flagResImg;
    }

    public void setFlagResImg(int flagResImg) {
        this.flagResImg = flagResImg;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public Event(Types type, String name, Date date, int price, String description, int flagResImg, boolean isBought) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.price = price;
        this.description = description;
        this.flagResImg = flagResImg;
        this.isBought = isBought;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", flagResImg=" + flagResImg +
                ", isBought=" + isBought +
                '}';
    }

    public enum Types {
        SHOW("Cпектакль"),
        CONCERT("Концерт"),
        OPERA("Опера"),
        MUSICAL("Мьюзикл");
        String name;

        Types (String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Types{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
