package crawler;

import adapter.DatabaseStorage;
import domain.EntityInterface;
import domain.Link;
import domain.Product;
import java.util.ArrayList;
import java.util.List;

public class Crawler {

    private final DatabaseStorage<Product> productStorage;
    private final PriceFetcher fetcher;

    public Crawler() {
        this.productStorage = new DatabaseStorage<>(Product.class);
        this.fetcher = new PriceFetcher();
    }

    public void run() {
        System.out.println("\n===================================================");
        System.out.println("  CRAWLER DE PREÇOS - INICIANDO");
        System.out.println("===================================================\n");

        ArrayList<EntityInterface> entities = productStorage.listAll();
        if (entities.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        for (EntityInterface entity : entities) {
            processProduct((Product) entity);
        }

        System.out.println("\n===================================================");
        System.out.println("  CRAWLER FINALIZADO");
        System.out.println("===================================================\n");
    }

    private void processProduct(Product product) {
        System.out.println("--------------------------------------------------");
        System.out.println("Produto: " + product.getName());
        System.out.println("--------------------------------------------------");

        List<Link> links = product.getLinks();
        if (links == null || links.isEmpty()) {
            System.out.println("  [AVISO] Nenhum link cadastrado.");
            return;
        }

        List<CrawlerResult> results = new ArrayList<>();
        for (Link link : links) {
            System.out.println("  Acessando " + link.getStore() + "...");
            Float price = fetcher.fetchPrice(link.getStore(), link.getUrl());
            CrawlerResult result = new CrawlerResult(link.getStore(), link.getUrl(), price);
            results.add(result);
            System.out.println("  -> " + result);
        }

        CrawlerResult lowest = findLowest(results);
        if (lowest == null) {
            System.out.println("  [AVISO] Nenhum preço obtido.\n");
            return;
        }

        System.out.println("\n  *** Menor preço: R$ " + String.format("%.2f", lowest.getPrice())
                + " na " + lowest.getStore() + " ***");

        product.setLowestPrice(lowest.getPrice(), lowest.getStore());
        productStorage.save(product);
        System.out.println("  Histórico atualizado.\n");
    }

    private CrawlerResult findLowest(List<CrawlerResult> results) {
        CrawlerResult lowest = null;
        for (CrawlerResult r : results) {
            if (!r.hasPrice()) continue;
            if (lowest == null || r.getPrice() < lowest.getPrice()) lowest = r;
        }
        return lowest;
    }
}