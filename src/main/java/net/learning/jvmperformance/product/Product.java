package net.learning.jvmperformance.product;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Xuesong Mei on 10/11/15.
 */
public class Product {
    public final String id;
    public String name;

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void main(String[] args) {
        System.out.println("Running...");

        try {
            Set<Product> set = new HashSet<>();
            int count = 100000;

            while (true) {
                for (int i = 0; i < count; i++) {
                    Product sample = new Product("" +i, "iWatch");
                    set.add(sample);
                }

                for (int i = 0; i < count; i++) {
                    Product sample = new Product("" +i, "iWatch");
                    set.remove(sample);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
