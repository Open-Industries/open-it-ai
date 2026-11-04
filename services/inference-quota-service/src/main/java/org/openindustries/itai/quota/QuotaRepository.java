package org.openindustries.itai.quota;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class QuotaRepository {
    private final ConcurrentHashMap<UUID, InferenceQuota> accounts = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<InferenceUsage> records = new CopyOnWriteArrayList<>();

    public InferenceQuota saveAccount(InferenceQuota account) {
        accounts.put(account.id, account);
        return account;
    }

    public Optional<InferenceQuota> findAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public InferenceUsage saveRecord(InferenceUsage record) {
        records.add(record);
        return record;
    }

    public List<InferenceUsage> recordsOf(UUID accountId) {
        return records.stream().filter(r -> r.accountId.equals(accountId)).toList();
    }
}
