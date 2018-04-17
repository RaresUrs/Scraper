package com.raresurs.sainsburysscraper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raresurs.sainsburysscraper.model.Product;
import com.raresurs.sainsburysscraper.scraper.ProductGridScraper;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String args[]) throws IOException {
        ProductGridScraper scraper = new ProductGridScraper(args[0]);
        List<Product> products = scraper.getProducts();

        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getUnitPrice();
        }
        String formattedTotalPrice = String.format("%.02f", totalPrice);


        Gson gsonBuilder = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        String serialisedProducts = gsonBuilder.toJson(products);
        System.out.print(serialisedProducts);
    }
}
