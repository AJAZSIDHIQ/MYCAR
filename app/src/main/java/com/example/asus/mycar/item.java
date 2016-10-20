package com.example.asus.mycar;

/**
 * Created by ASUS on 11-10-2016.
 */

public class item {
    private String id;
    private String company;
    private String name;
    private String price;

    public item( String id,String company, String name ,String price) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.price = price;
    }
  //  public int getimageid() {
   //     return imageid;
 //   }
  //  public void setimageid(int imageid) {
  //      this.imageid = imageid;
   // }
    public String getid() {
      return id;
  }
    public void setid(String id) {
        this.id = id;
    }
    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }
    public String getcompany() {
        return company;
    }
    public void setcompany(String company) {
        this.company = company;
    }
    public String getprice() {
        return price;
    }
    public void setprice(String price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return company + "\n" + name;
    }
}
