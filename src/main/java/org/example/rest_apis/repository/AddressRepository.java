package org.example.rest_apis.repository;

import org.example.rest_apis.model.Address;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AddressRepository {

    private final Map<Long, Address> addresses = new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong(0);

    public Address save(Address address) {
        if (address.getId() == null) {
            address.setId(idGenerator.incrementAndGet());
        }

        addresses.put(address.getId(), address);

        return address;
    }
}
