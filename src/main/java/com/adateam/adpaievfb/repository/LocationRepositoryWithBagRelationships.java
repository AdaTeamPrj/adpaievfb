package com.adateam.adpaievfb.repository;

import com.adateam.adpaievfb.domain.Location;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface LocationRepositoryWithBagRelationships {
    Optional<Location> fetchBagRelationships(Optional<Location> location);

    List<Location> fetchBagRelationships(List<Location> locations);

    Page<Location> fetchBagRelationships(Page<Location> locations);
}
