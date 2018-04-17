package com.raresurs.sainsburysscraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

abstract class Scrapper {

    private final Document doc;

    Scrapper(String url) throws IOException {
        try {
            this.doc = Jsoup.connect(url).get();
            if (this.doc == null) {
                System.err.println(String.format("Document retrieved was empty. URL: %s", url));
            }
        } catch (IOException e) {
            System.err.println(String.format("There has been an error while fetching the HTML code from %s", url));
            throw e;
        }
    }

    Document getDoc() {
        return doc.clone();
    }
}
