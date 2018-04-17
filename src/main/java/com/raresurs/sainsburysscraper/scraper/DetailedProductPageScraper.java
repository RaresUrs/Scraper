package com.raresurs.sainsburysscraper.scraper;

import com.raresurs.sainsburysscraper.model.NutritionalInformation;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.stream.Collectors;

public class DetailedProductPageScraper extends Scrapper {

    DetailedProductPageScraper(String url) throws IOException {
        super(url);
    }

    public NutritionalInformation getNutritionalInformation() {
        Element body = getDoc().body();
        Element infoElement = body.getElementById("information");

        Elements elements = infoElement.getElementsByClass("productText");
        String description = getDescription(elements);
        String calories = getCalories(elements);

        NutritionalInformation nutritionalInformation = new NutritionalInformation();
        nutritionalInformation.setDescription(description);
        nutritionalInformation.setCalories(calories);
        ;
        return nutritionalInformation;
    }

    private String getCalories(Elements elements) {
        return elements.get(1).getElementsByTag("td").text().contains("kcal") ?
                elements.get(1).getElementsByTag("td").text().substring(8, 14) :
                elements.get(1).getElementsByTag("td").text();

    }

    private String getDescription(Elements elements) {
        return elements.first()
                .getElementsByTag("p")
                .stream()
                .filter(p -> !p.text().isEmpty())
                .collect(Collectors.toList())
                .get(0)
                .text();
    }
}
