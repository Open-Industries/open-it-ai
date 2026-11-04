package org.openindustries.itai.quota;

import java.time.Instant;
import java.util.UUID;

/** One guarded consumption record — the auditable evidence of a draw on a InferenceQuota. */
public class InferenceUsage {
    public UUID id;
    public UUID accountId;
    public String usageType;
    public long units;
    public long remainingAfter;
    public Instant recordedAt;

    public static InferenceUsage of(UUID accountId, String usageType, long units, long remainingAfter) {
        InferenceUsage r = new InferenceUsage();
        r.id = UUID.randomUUID();
        r.accountId = accountId;
        r.usageType = usageType;
        r.units = units;
        r.remainingAfter = remainingAfter;
        r.recordedAt = Instant.now();
        return r;
    }
}
