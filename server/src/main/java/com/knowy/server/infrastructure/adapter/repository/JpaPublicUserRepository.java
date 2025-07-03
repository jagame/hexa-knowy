package com.knowy.server.infrastructure.adapter.repository;

import com.knowy.server.application.domain.ProfileImage;
import com.knowy.server.application.domain.PublicUser;
import com.knowy.server.application.port.persistence.KnowyUserNotFoundException;
import com.knowy.server.application.port.persistence.PublicUserRepository;
import com.knowy.server.infrastructure.adapter.repository.dao.JpaProfileImageDao;
import com.knowy.server.infrastructure.adapter.repository.dao.JpaPublicUserDao;
import com.knowy.server.infrastructure.adapter.repository.entity.JpaProfileImageEntity;
import com.knowy.server.infrastructure.adapter.repository.entity.JpaPublicUserEntity;
import com.knowy.server.infrastructure.adapter.repository.mapper.EntityMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaPublicUserRepository implements PublicUserRepository {

    private final JpaPublicUserDao publicUserDao;
    private final EntityMapper<PublicUser, JpaPublicUserEntity> publicUserMapper;
    private final JpaProfileImageDao profileImageDao;
    private final EntityMapper<ProfileImage, JpaProfileImageEntity> profileImageMapper;

    public JpaPublicUserRepository(
            JpaPublicUserDao publicUserDao, EntityMapper<PublicUser, JpaPublicUserEntity> publicUserMapper,
            JpaProfileImageDao profileImageDao, EntityMapper<ProfileImage, JpaProfileImageEntity> profileImageMapper
    ) {
        this.publicUserDao = publicUserDao;
        this.publicUserMapper = publicUserMapper;
        this.profileImageDao = profileImageDao;
        this.profileImageMapper = profileImageMapper;
    }

    @Override
    public Optional<PublicUser> findById(int id) throws KnowyPublicUserPersistenceException {
        try {
            return publicUserDao.findById(id)
                    .map(publicUserMapper::toDomain);
        } catch (DataAccessException ex) {
            throw new KnowyPublicUserPersistenceException(ex);
        }
    }

    @Override
    public Optional<PublicUser> findByNickname(String nickname) throws KnowyPublicUserPersistenceException {
        try {
            return publicUserDao.findByNickname(nickname)
                    .map(publicUserMapper::toDomain);
        } catch (DataAccessException ex) {
            throw new KnowyPublicUserPersistenceException(ex);
        }
    }

    @Override
    public void update(PublicUser publicUser) throws KnowyPublicUserPersistenceException {
        try {
            publicUserDao.save(publicUserMapper.toEntity(publicUser));
        } catch (DataAccessException ex) {
            throw new KnowyPublicUserPersistenceException(ex);
        }
    }

    @Override
    public Optional<ProfileImage> findProfileImageById(int id) throws KnowyPublicUserPersistenceException {
        try {
            return profileImageDao.findById(id)
                    .map(profileImageMapper::toDomain);
        } catch (DataAccessException ex) {
            throw new KnowyPublicUserPersistenceException(ex);
        }
    }
}
