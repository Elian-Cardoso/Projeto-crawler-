package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class PriceFetcher {

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) " +
            "Chrome/124.0.0.0 Safari/537.36";

    public Float fetchPrice(String store, String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(15000)
                    .get();
            String rawPrice = extractPrice(store, doc);
            if (rawPrice == null || rawPrice.isBlank()) {
                System.out.println("  [AVISO] Preço não encontrado em: " + url);
                return null;
            }
            return parsePrice(rawPrice);
        } catch (IOException e) {
            System.out.println("  [ERRO] Falha ao acessar " + url + " -> " + e.getMessage());
            return null;
        }
    }

    private String extractPrice(String store, Document doc) {
        String s = store.toLowerCase();
        if (s.contains("amazon"))
            return trySelectors(doc, ".a-price-whole", "#priceblock_ourprice", ".a-offscreen");
        if (s.contains("kabum"))
            return trySelectors(doc, ".finalPrice", "[class*='finalPrice']", "[class*='price']");
        if (s.contains("magalu") || s.contains("magazine"))
            return trySelectors(doc, "[data-testid='price-value']", "p[class*='price']", "[class*='Price']");
        if (s.contains("mercado"))
            return trySelectors(doc, ".andes-money-amount__fraction", ".price-tag-fraction");
        if (s.contains("casas"))
            return trySelectors(doc, "[data-testid='price']", ".price__best-price", "[class*='price']");
        if (s.contains("americanas"))
            return trySelectors(doc, "[data-testid='price']", "[class*='Price']");
        return trySelectors(doc, "[class*='price']", "[class*='preco']", "[class*='valor']");
    }

    private String trySelectors(Document doc, String... selectors) {
        for (String selector : selectors) {
            try {
                Element el = doc.selectFirst(selector);
                if (el != null && !el.text().isBlank()) return el.text();
            } catch (Exception ignored) {}
        }
        return null;
    }

    private Float parsePrice(String raw) {
        try {
            String cleaned = raw.replaceAll("[^\\d.,]", "").trim();
            if (cleaned.contains(",") && cleaned.contains("."))
                cleaned = cleaned.replace(".", "").replace(",", ".");
            else if (cleaned.contains(","))
                cleaned = cleaned.replace(",", ".");
            if (cleaned.endsWith("."))
                cleaned = cleaned.substring(0, cleaned.length() - 1);
            return Float.parseFloat(cleaned);
        } catch (NumberFormatException e) {
            System.out.println("  [ERRO] Não foi possível converter preço: '" + raw + "'");
            return null;
        }
    }
}