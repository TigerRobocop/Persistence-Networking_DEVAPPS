package com.tigerrobocop.liv.pn.Model;

import java.io.Serializable;

/**
 * Created by Livia on 21/08/2017.
 */

public class APOD implements Serializable {

    public APOD() {

    }

    public APOD(String date, String title, String explanation, String url, String copyright, String mediaType) {

        this.date = date;
        this.title = title;
        this.explanation = explanation;
        this.url = url;
        this.copyright = copyright;
        this.media_type = mediaType;
    }

    public String date;
    public String title;
    public String explanation;
    public String url;
    public String copyright;
    public String media_type;

    // unused from JSON response
    public String hdurl;
    public String service_version;
}
