package org.openindustries.itai.quota;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class QuotaService {

    @Inject
    QuotaRepository repository;

    public InferenceQuota open(OpenInferenceQuotaRequest request) {
        return repository.saveAccount(InferenceQuota.open(request.teamCode(), request.budgetUnits()));
    }

    public InferenceQuota get(UUID id) {
        return repository.findAccount(id)
                .orElseThrow(() -> new DomainException(404, "InferenceQuota not found: " + id));
    }

    /**
     * Draws from the account. Invariant: the balance/capacity never goes negative —
     * an over-drawing operation is rejected (422) and nothing is recorded.
     */
    public InferenceUsage consume(UUID accountId, CreateInferenceUsageRequest request) {
        InferenceQuota account = get(accountId);
        if (!"ACTIVE".equals(account.status)) {
            throw new DomainException(409, "InferenceQuota is not ACTIVE");
        }
        if (request.units() > account.budgetUnits) {
            throw new DomainException(422, "Insufficient capacity: requested " + request.units()
                    + " exceeds available " + account.budgetUnits);
        }
        account.budgetUnits -= request.units();
        repository.saveAccount(account);
        return repository.saveRecord(InferenceUsage.of(accountId, request.usageType(), request.units(), account.budgetUnits));
    }

    public List<InferenceUsage> recordsOf(UUID accountId) {
        get(accountId);
        return repository.recordsOf(accountId);
    }
}
