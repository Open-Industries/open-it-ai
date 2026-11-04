package org.openindustries.itai.dataset;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DatasetService {

    @Inject
    DatasetRepository repository;

    public Dataset create(CreateDatasetRequest request) {
        return repository.save(Dataset.create(request.name(), request.dataType(), request.recordsCount()));
    }

    public Dataset get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Dataset not found: " + id));
    }

    public List<Dataset> list() {
        return repository.findAll();
    }

    public Dataset approve(UUID id) {
        Dataset entity = get(id);
        if (!"DRAFT".equals(entity.status)) {
            throw new DomainException(409, "Only a DRAFT dataset can transition via approve");
        }
        entity.status = "APPROVED";
        return repository.save(entity);
    }
}
