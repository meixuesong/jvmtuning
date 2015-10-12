/*
 * Copyright (c) 2013,2014 Scott Oaks. All rights reserved.
 */

package net.learning.jvmperformance.batching;

import net.learning.jvmperformance.batching.stock.StockPriceHistory;
import net.learning.jvmperformance.batching.stock.StockPriceUtils;
import net.learning.jvmperformance.batching.stockimpl.MockStockPriceEntityManagerFactory;
import net.learning.jvmperformance.batching.stockimpl.StockPriceHistoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class StockPriceHistoryBatcher {
    private static final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
    private static DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        int stockCount = (args.length < 1) ? 50000 : Integer.parseInt(args[0]);
        Date startDate = (args.length < 2) ? df.parse("01/01/10") : df.parse(args[1]);
        Date endDate = (args.length < 3) ? df.parse("12/31/15") : df.parse(args[2]);
        int saveCount = (args.length < 4) ? 100 : Integer.parseInt(args[3]);

        System.out.println("Num stocks " + stockCount + " " + startDate + " " + endDate);

        EntityManager em = getEntityManager();
        StockPriceHistory[] savedHistory = new StockPriceHistory[saveCount];

        for (int i = 0; i < stockCount; i++) {
            String stockCode = StockPriceUtils.getStockCode(i);
            StockPriceHistory sph = new StockPriceHistoryImpl(stockCode, startDate, endDate, em);
//            printHistory(sph);

            if (saveCount > 0) {
                savedHistory[i % saveCount] = sph;
            }
        }
    }

    private static EntityManager getEntityManager() {
        EntityManagerFactory emf = new MockStockPriceEntityManagerFactory("MockEntityManager");
        return emf.createEntityManager();
    }

    private static void printHistory(StockPriceHistory sph) {
        System.out.println("For " + sph.getSymbol()
                + ": High " + nf.format(sph.getHighPrice())
                + ", Low " + nf.format(sph.getLowPrice())
                + ", Standard Deviation: " + sph.getStdDev().doubleValue());
    }
}
