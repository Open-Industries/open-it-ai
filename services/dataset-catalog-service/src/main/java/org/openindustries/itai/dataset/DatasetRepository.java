package org.openindustries.itai.dataset;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class DatasetRepository {
    private final ConcurrentHashMap<UUID, Dataset> store = new ConcurrentHashMap<>();

    public Dataset save(Dataset entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<Dataset> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Dataset> findAll() {
        return List.copyOf(store.values());
    }
}
