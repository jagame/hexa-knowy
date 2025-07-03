package com.knowy.server.infrastructure.adapter.repository;

import com.knowy.server.application.domain.Email;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.port.persistence.KnowyUserNotFoundException;
import com.knowy.server.application.port.persistence.PrivateUserRepository;
import com.knowy.server.infrastructure.adapter.repository.dao.JpaPrivateUserEntityDao;
import com.knowy.server.infrastructure.adapter.repository.entity.JpaPrivateUserEntity;
import com.knowy.server.infrastructure.adapter.repository.mapper.EntityMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class JpaPrivateUserRepository implements PrivateUserRepository {

    private final JpaPrivateUserEntityDao privateUserDao;
    private final EntityMapper<PrivateUser, JpaPrivateUserEntity> privateUserMapper;

    public JpaPrivateUserRepository(
            JpaPrivateUserEntityDao privateUserDao, EntityMapper<PrivateUser, JpaPrivateUserEntity> privateUserMapper
    ) {
        this.privateUserDao = privateUserDao;
        this.privateUserMapper = privateUserMapper;
    }

    @Override
    public Optional<PrivateUser> findById(int id) throws KnowyPrivateUserPersistenceException {
        try {
            return privateUserDao.findById(id)
                    .map(privateUserMapper::toDomain);
        } catch (DataAccessException ex) {
            throw new KnowyPrivateUserPersistenceException(ex);
        }
    }

    @Override
    public Optional<PrivateUser> findByEmail(Email email) throws KnowyPrivateUserPersistenceException {
        try {
            return privateUserDao.findByEmail(email.value())
                    .map(privateUserMapper::toDomain);
        } catch (DataAccessException ex) {
            throw new KnowyPrivateUserPersistenceException(ex);
        }
    }

    @Override
    public Optional<PrivateUser> findByToken(String token) throws KnowyPrivateUserPersistenceException {
        try {
            return privateUserDao.findByToken(token)
                    .map(privateUserMapper::toDomain);
        } catch (DataAccessException ex) {
            throw new KnowyPrivateUserPersistenceException(ex);
        }
    }

    @Override
    public void update(PrivateUser privateUser) throws KnowyPrivateUserPersistenceException {
        try {
            privateUserDao.save(privateUserMapper.toEntity(privateUser));
        } catch (DataAccessException ex) {
            throw new KnowyPrivateUserPersistenceException(ex);
        }
    }
}
