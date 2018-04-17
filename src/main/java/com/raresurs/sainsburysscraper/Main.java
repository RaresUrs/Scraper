package com.raresurs.sainsburysscraper;

import com.raresurs.sainsburysscraper.model.Product;
import com.raresurs.sainsburysscraper.scraper.ProductGridScraper;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main (String args[]) throws IOException {
        ProductGridScraper scraper = new ProductGridScraper(args[0]);
        List<Product> products = scraper.getProducts();
        System.out.println(products);
    }
}
