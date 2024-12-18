package kz.hxncus.mc.advancedapi.api.data.repository;

import java.util.Collection;

import com.google.common.base.Optional;

/**
 * Базовый интерфейс для репозиториев
 * @param <ID> Тип идентификатора
 * @param <T>  Тип сущности
 */
public interface Repository<ID, T> {
    /**
     * Сохраняет сущность
     * @param entity Сущность для сохранения
     * @return Сохраненная сущность
     * @throws RepositoryException при ошибке сохранения
     */
    T save(T entity);

    /**
     * Сохраняет коллекцию сущностей
     * @param entities Коллекция сущностей
     * @return Коллекция сохраненных сущностей
     * @throws RepositoryException при ошибке сохранения
     */
    Collection<T> saveAll(Collection<T> entities);

    /**
     * Находит сущность по ID
     * @param id Идентификатор
     * @return Optional с найденной сущностью
     * @throws RepositoryException при ошибке поиска
     */
    Optional<T> findById(ID id);

    /**
     * Проверяет существование сущности по ID
     * @param id Идентификатор
     * @return true если сущность существует
     */
    boolean existsById(ID id);

    /**
     * Возвращает все сущности
     * @return Коллекция всех сущностей
     */
    Collection<T> findAll();

    /**
     * Возвращает количество сущностей
     * @return Количество сущностей
     */
    long count();

    /**
     * Удаляет сущность по ID
     * @param id Идентификатор
     * @throws RepositoryException при ошибке удаления
     */
    void deleteById(ID id);

    /**
     * Удаляет сущность
     * @param entity Сущность для удаления
     * @throws RepositoryException при ошибке удаления
     */
    void delete(T entity);

    /**
     * Удаляет все сущности
     * @throws RepositoryException при ошибке удаления
     */
    void deleteAll();
}
