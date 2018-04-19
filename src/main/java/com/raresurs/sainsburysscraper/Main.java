package com.raresurs.sainsburysscraper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raresurs.sainsburysscraper.model.Product;
import com.raresurs.sainsburysscraper.scraper.ProductGridScraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.format;

public class Main {

    public static void main(String args[]) throws IOException {
        ProductGridScraper scraper = new ProductGridScraper(args[0]);
        List<Product> products = scraper.getProducts();

        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getUnitPrice();
        }

        HashMap<String, List> result = new HashMap<>();
        result.put("result", products);

        Gson gsonBuilder = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        String serialisedProducts = gsonBuilder.toJson(result);

        System.out.println(serialisedProducts);
        System.out.println(String.format("total: %s", format("%.02f", totalPrice)));
    }
}
