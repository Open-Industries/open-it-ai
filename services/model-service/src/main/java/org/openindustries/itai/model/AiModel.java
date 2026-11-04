package org.openindustries.itai.model;

import java.time.Instant;
import java.util.UUID;

/**
 * AiModel aggregate — AI model registry with lifecycle governance.
 *
 * Privacy by design (ISO/IEC 27002 A.5.34): secret identifiers are validated at
 * creation and NEVER persisted or returned in clear — only a masked form is kept.
 * All identifiers in this reference implementation are synthetic.
 */
public class AiModel {
    public UUID id;
        public String name;
        public String framework;
        public String ownerTeam;
    public String status;
    public Instant createdAt;

    public static AiModel create(String name, String framework, String ownerTeam) {
        AiModel e = new AiModel();
        e.id = UUID.randomUUID();
        e.name = name;
        e.framework = framework;
        e.ownerTeam = ownerTeam;
        e.status = "REGISTERED";
        e.createdAt = Instant.now();
        return e;
    }
}
