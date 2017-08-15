package com.tigerrobocop.liv.pn.Model;

import java.io.Serializable;

/**
 * Created by Livia on 14/08/2017.
 */

public class Cat implements Serializable {

    public Cat(String title, String link, String date_taken, String url) {
        this.title = title;
        this.link = link;
        this.date_taken = date_taken;
        this.url = url;
    }

    public String title;
    public String link;
    public String date_taken;
    public String url;

}
