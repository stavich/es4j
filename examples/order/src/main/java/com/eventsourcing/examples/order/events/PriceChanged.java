/**
 * Copyright 2016 Eventsourcing team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 */
package com.eventsourcing.examples.order.events;

import com.eventsourcing.Event;
import com.eventsourcing.annotations.Index;
import com.eventsourcing.hlc.HybridTimestamp;
import com.eventsourcing.index.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

import static com.eventsourcing.index.IndexEngine.IndexFeature.*;

@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
public class PriceChanged extends Event {
    @Getter @Setter
    private UUID id;
    @Getter @Setter
    private BigDecimal price;

    @Index({EQ})
    public static final SimpleAttribute<PriceChanged, UUID> REFERENCE_ID = new SimpleAttribute<PriceChanged, UUID>("referenceId") {
        public UUID getValue(PriceChanged priceChanged, QueryOptions queryOptions) {
            return priceChanged.id();
        }
    };

    @Index({EQ, LT, GT})
    public static final SimpleAttribute<PriceChanged, HybridTimestamp> TIMESTAMP = new SimpleAttribute<PriceChanged, HybridTimestamp>("timestamp") {
        public HybridTimestamp getValue(PriceChanged priceChanged, QueryOptions queryOptions) {
            return priceChanged.timestamp();
        }
    };
}