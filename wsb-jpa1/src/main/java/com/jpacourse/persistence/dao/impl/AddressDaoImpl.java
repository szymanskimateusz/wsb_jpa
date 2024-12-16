package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class AddressDaoImpl extends AbstractDao<AddressEntity, Long> implements AddressDao
{

}
