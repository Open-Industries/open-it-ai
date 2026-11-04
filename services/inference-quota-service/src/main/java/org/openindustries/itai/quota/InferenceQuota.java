package org.openindustries.itai.quota;

import java.util.UUID;

/**
 * InferenceQuota aggregate — the guarded resource account of this core: a balance/capacity that
 * consumption operations draw from. Core invariant (ADR-0004): it NEVER goes negative;
 * an operation that would overdraw it is rejected and records nothing.
 */
public class InferenceQuota {
    public UUID id;
        public String teamCode;
    public long budgetUnits;
    public String status;

    public static InferenceQuota open(String teamCode, long budgetUnits) {
        InferenceQuota a = new InferenceQuota();
        a.id = UUID.randomUUID();
        a.teamCode = teamCode;
        a.budgetUnits = budgetUnits;
        a.status = "ACTIVE";
        return a;
    }
}
