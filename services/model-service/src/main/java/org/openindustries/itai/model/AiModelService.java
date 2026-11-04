package org.openindustries.itai.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AiModelService {

    @Inject
    AiModelRepository repository;

    public AiModel create(CreateAiModelRequest request) {
        return repository.save(AiModel.create(request.name(), request.framework(), request.ownerTeam()));
    }

    public AiModel get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "AiModel not found: " + id));
    }

    public List<AiModel> list() {
        return repository.findAll();
    }

    public AiModel approve(UUID id) {
        AiModel entity = get(id);
        if ("RETIRED".equals(entity.status)) {
            throw new DomainException(409, "A retired aimodel is terminal");
        }
        if ("APPROVED".equals(entity.status)) {
            throw new DomainException(409, "AiModel already APPROVED");
        }
        entity.status = "APPROVED";
        return repository.save(entity);
    }

    public AiModel retire(UUID id) {
        AiModel entity = get(id);
        if ("RETIRED".equals(entity.status)) {
            throw new DomainException(409, "A retired aimodel is terminal");
        }
        if ("RETIRED".equals(entity.status)) {
            throw new DomainException(409, "AiModel already RETIRED");
        }
        entity.status = "RETIRED";
        return repository.save(entity);
    }
}
