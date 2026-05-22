package crawler;

public class CrawlerResult {
    private final String store;
    private final String url;
    private final Float price;

    public CrawlerResult(String store, String url, Float price) {
        this.store = store;
        this.url = url;
        this.price = price;
    }

    public String getStore() { return store; }
    public String getUrl() { return url; }
    public Float getPrice() { return price; }

    public boolean hasPrice() {
        return price != null && price > 0;
    }

    @Override
    public String toString() {
        if (hasPrice()) {
            return store + ": R$ " + String.format("%.2f", price);
        }
        return store + ": preço não encontrado";
    }
}