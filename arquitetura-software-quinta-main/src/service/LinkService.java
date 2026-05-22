package service;

import adapter.DatabaseStorage;
import domain.Link;

public class LinkService extends BaseService {
    public LinkService() {
        this.armazenamento = new DatabaseStorage<>(Link.class);
    }
}