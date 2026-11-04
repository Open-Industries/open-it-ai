package org.openindustries.itai.dataset;

import java.util.UUID;

/** Dataset aggregate — Dataset catalogue — metadata only, never content. */
public class Dataset {
    public UUID id;
        public String name;
        public String dataType;
        public long recordsCount;
    public String status;

    public static Dataset create(String name, String dataType, long recordsCount) {
        Dataset e = new Dataset();
        e.id = UUID.randomUUID();
        e.name = name;
        e.dataType = dataType;
        e.recordsCount = recordsCount;
        e.status = "DRAFT";
        return e;
    }
}
