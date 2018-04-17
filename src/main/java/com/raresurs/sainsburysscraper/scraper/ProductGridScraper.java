package com.raresurs.sainsburysscraper.scraper;

import com.raresurs.sainsburysscraper.model.NutritionalInformation;
import com.raresurs.sainsburysscraper.model.Product;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductGridScraper extends Scrapper {

    public ProductGridScraper(String url) throws IOException {
        super(url);
    }

    public List<Product> getProducts() {
        Element body = getDoc().body();
        Elements gridItems = body.getElementsByClass("gridItem");

        return gridItems.stream().map(gridItem2Product()).collect(Collectors.toList());
    }

    private Function<Element, Product> gridItem2Product() {
        return element -> {
            try {
                Product product = new Product();
                String title = getProductTitle(element);
                Double price = getProductDescription(element);
                product.setTitle(title);
                product.setUnitPrice(price);

                String productDetailsURL = getProductDetailURL(element);
                DetailedProductPageScraper helperScraper = new DetailedProductPageScraper(productDetailsURL);
                NutritionalInformation nutritionalInformation = helperScraper.getNutritionalInformation();
                product.setCalories(nutritionalInformation.getCalories());
                product.setDescription(nutritionalInformation.getDescription());

                return product;
            } catch (IOException e) {
                throw new RuntimeException();
            }
        };
    }

    private String getProductDetailURL(Element element) {
        return element.getElementsByClass("productNameAndPromotions")
                .first()
                .child(0)
                .child(0)
                .absUrl("href");
    }

    private Double getProductDescription(Element element) {
        return Double.parseDouble(element
                .getElementsByClass("pricePerUnit")
                .first()
                .text()
                .replace("£", "")
                .replace("/unit", ""));
    }

    private String getProductTitle(Element element) {
        return element.getElementsByClass("productNameAndPromotions")
                .first()
                .child(0)
                .child(0)
                .text();
    }

}
