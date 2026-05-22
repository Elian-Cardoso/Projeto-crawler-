package domain;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product implements EntityInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "uuid", length = 36)
    private UUID uuid;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private Float price;

    @Column(name = "store")
    private String store;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_price")
    private Date datePrice;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Price> historicalPrice = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Link> links = new ArrayList<>();

    public Product() {}

    public Product(String sku, String name) {
        this.sku = sku;
        this.name = name;
    }

    public void setLowestPrice(Float price, String store) {
        if (this.price != null && this.datePrice != null && this.store != null) {
            Price oldPrice = new Price(this.price, this.store, this.datePrice);
            oldPrice.setProduct(this);
            historicalPrice.add(oldPrice);
        }
        this.price = price;
        this.store = store;
        this.datePrice = new Date();
    }

    public void addLink(Link link) {
        link.setProduct(this);
        this.links.add(link);
    }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Float getPrice() { return price; }
    public String getStore() { return store; }
    public Date getDatePrice() { return datePrice; }
    public List<Price> getHistoricalPrice() { return historicalPrice; }
    public List<Link> getLinks() { return links; }

    @Override
    public UUID getUUID() { return uuid; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=============================\n");
        sb.append("Produto : ").append(name).append("\n");
        sb.append("SKU     : ").append(sku).append("\n");
        if (price != null) {
            sb.append("Menor preço atual: R$ ").append(String.format("%.2f", price)).append("\n");
            sb.append("Loja    : ").append(store).append("\n");
            sb.append("Data    : ").append(datePrice).append("\n");
        }
        sb.append("Links cadastrados:\n");
        for (Link link : links) {
            sb.append("  - ").append(link.getStore()).append(": ").append(link.getUrl()).append("\n");
        }
        sb.append("Histórico de preços:\n");
        if (historicalPrice.isEmpty()) {
            sb.append("  (sem histórico anterior)\n");
        } else {
            for (Price p : historicalPrice) {
                sb.append("  - R$ ").append(String.format("%.2f", p.getPrice()))
                  .append(" | ").append(p.getStore())
                  .append(" | ").append(p.getDate()).append("\n");
            }
        }
        sb.append("=============================");
        return sb.toString();
    }
}