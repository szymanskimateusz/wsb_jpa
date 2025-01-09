package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.entity.AddressEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressDaoImpl extends AbstractDao<AddressEntity, Long> implements AddressDao
{
    @Override
    public List<AddressEntity> findByAddress(String city, String addressLine1, String postalCode) {
        return entityManager.createQuery("SELECT city, addressLine1, postalCode FROM AddressEntity" +
                        " WHERE city = :city AND addressLine1 = :addressLine1 AND postalCode = :postalCode", AddressEntity.class)
                .setParameter("city", city)
                .setParameter("addressLine1", addressLine1)
                .setParameter("postalCode", postalCode)
                .getResultList();
    }
}
