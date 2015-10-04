package de.solugo.scrumpoker.data.repository;

import de.solugo.scrumpoker.data.entity.Base;
import de.solugo.scrumpoker.util.BeanMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class BaseRepository<T extends Base> {
    private final Class<T> type;

    @Autowired
    private BeanMapper beanMapper;

    @PersistenceContext
    protected EntityManager entityManager;

    public BaseRepository(@NonNull final Class<T> type) {
        this.type = type;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<T> findAll() {
        final CriteriaQuery<T> query = this.createCriteriaQuery();
        query.from(type);
        return entityManager.createQuery(query).getResultList();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public T findById(@NonNull final Long id) {
        return entityManager.find(type, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public T save(final T value) {
        return entityManager.merge(value);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public T merge(@NonNull final T value) {
        return this.merge(value.getId(), value);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public T merge(final Long id, @NonNull final Object value) {
        try {
            final T target = id != null ? findById(id) : type.newInstance();
            beanMapper.map(value, target);
            return entityManager.merge(target);
        } catch (final IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    protected TypedQuery<T> createQuery(@NonNull final String query) {
        return entityManager.createQuery(query, type);
    }

    protected CriteriaQuery<T> createCriteriaQuery() {
        return entityManager.getCriteriaBuilder().createQuery(type);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    protected T findByQuery(final TypedQuery<T> query) {
        try {
            return query.getSingleResult();
        } catch (final NoResultException e) {
            return null;
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    protected List<T> findAllByQuery(final TypedQuery<T> query) {
        try {
            return query.getResultList();
        } catch (final NoResultException e) {
            return null;
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    protected List<T> listByQuery(final TypedQuery<T> query) {
        return query.getResultList();
    }
}
