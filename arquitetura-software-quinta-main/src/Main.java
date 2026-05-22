import adapter.DatabaseStorage;
import crawler.Crawler;
import domain.EntityInterface;
import domain.Link;
import domain.Product;
import service.ProductService;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        DatabaseStorage<Product> storage = new DatabaseStorage<>(Product.class);
        ArrayList<EntityInterface> existentes = storage.listAll();

        if (existentes.isEmpty()) {
            System.out.println("Cadastrando produtos...\n");
            cadastrarProdutos(productService);
        } else {
            System.out.println("Produtos já cadastrados.\n");
        }

        System.out.println("=== Produtos cadastrados ===");
        productService.listAll();

        Crawler crawler = new Crawler();
        crawler.run();

        System.out.println("\n=== Produtos após crawler ===");
        productService.listAll();
    }

    private static void cadastrarProdutos(ProductService productService) {
        Product ps5 = new Product("PS5-001", "PlayStation 5");
        ps5.addLink(new Link("Amazon", "https://www.amazon.com.br/PlayStation-5-console-Standard/dp/B0CQTDB67H"));
        ps5.addLink(new Link("Kabum", "https://www.kabum.com.br/produto/364285/console-playstation-5-sony-slim-edicao-digital-1tb"));
        ps5.addLink(new Link("Magalu", "https://www.magazineluiza.com.br/console-playstation-5-sony-825gb-edicao-digital/p/237942900/ga/cons/"));
        productService.create(ps5);

        Product xbox = new Product("XBOX-001", "Xbox Series X 1TB");
        xbox.addLink(new Link("Amazon", "https://www.amazon.com.br/Xbox-Series-Console-1TB-RRT-00010/dp/B08H75RTZ8"));
        xbox.addLink(new Link("Casas Bahia", "https://www.casasbahia.com.br/console-xbox-series-x-1tb-microsoft-rrt-00010/p/1500404693"));
        xbox.addLink(new Link("Mercado Livre", "https://www.mercadolivre.com.br/console-xbox-series-x-1tb-microsoft/p/MLB18336789"));
        productService.create(xbox);

        Product notebook = new Product("NOTE-001", "Notebook Dell Inspiron 15 i5 16GB 512GB");
        notebook.addLink(new Link("Amazon", "https://www.amazon.com.br/Notebook-Dell-Inspiron-i5-1235U-Windows/dp/B0BVZPQB6B"));
        notebook.addLink(new Link("Kabum", "https://www.kabum.com.br/produto/475965/notebook-dell-inspiron-15-3520-intel-core-i5-1235u-8gb-256gb-ssd-windows-11-15-6-fhd-linux"));
        notebook.addLink(new Link("Magalu", "https://www.magazineluiza.com.br/notebook-dell-inspiron-15-3520-intel-core-i5-1235u-16gb-512gb-ssd-15-6-fhd-windows-11/p/238082900/in/noti/"));
        productService.create(notebook);

        Product iphone = new Product("IPH-001", "iPhone 15 128GB");
        iphone.addLink(new Link("Amazon", "https://www.amazon.com.br/Apple-iPhone-15-128GB-Preto/dp/B0CHX3QBCH"));
        iphone.addLink(new Link("Casas Bahia", "https://www.casasbahia.com.br/iphone-15-apple-128gb-preto-tela-6-1-camera-dupla-48mp-ios/p/1500422222"));
        iphone.addLink(new Link("Mercado Livre", "https://www.mercadolivre.com.br/apple-iphone-15-128-gb-preto/p/MLB25038561"));
        productService.create(iphone);

        Product tv = new Product("TV-001", "Smart TV Samsung 55\" 4K QLED");
        tv.addLink(new Link("Amazon", "https://www.amazon.com.br/Smart-TV-Samsung-Polegadas-QN55Q60CAGXZD/dp/B0BX8SN3QG"));
        tv.addLink(new Link("Kabum", "https://www.kabum.com.br/produto/411258/smart-tv-55-4k-samsung-qled-qn55q60cagxzd"));
        tv.addLink(new Link("Casas Bahia", "https://www.casasbahia.com.br/smart-tv-qled-4k-55-samsung-qn55q60cagxzd/p/1500333333"));
        productService.create(tv);

        System.out.println("Cadastro finalizado!\n");
    }
}