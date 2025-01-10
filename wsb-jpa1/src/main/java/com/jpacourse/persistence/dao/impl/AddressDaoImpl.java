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
        return entityManager.createQuery("select city, addressLine1, postalCode from AddressEntity" +
                        " where city = :city and addressLine1 = :addressLine1 and postalCode = :postalCode", AddressEntity.class)
                .setParameter("city", city)
                .setParameter("addressLine1", addressLine1)
                .setParameter("postalCode", postalCode)
                .getResultList();
    }
}
