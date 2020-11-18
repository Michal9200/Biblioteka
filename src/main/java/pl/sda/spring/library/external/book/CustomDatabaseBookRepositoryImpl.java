package pl.sda.spring.library.external.book;

import pl.sda.spring.library.model.SearchParams;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CustomDatabaseBookRepositoryImpl implements CustomDatabaseBookRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<BookEntity> findBasedOnSearchParams(SearchParams searchParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> query = criteriaBuilder.createQuery(BookEntity.class);
        Root<BookEntity> root = query.from(BookEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        if (searchParams.getTitle() != null && !searchParams.getTitle().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("title"), searchParams.getTitle()));
        }
        if(searchParams.getAuthor() != null && !searchParams.getAuthor().isEmpty()){
            predicates.add(criteriaBuilder.equal(root.get("author"), searchParams.getAuthor()));
        }
        if(searchParams.getIsbn() != null && !searchParams.getIsbn().isEmpty()){
            predicates.add(criteriaBuilder.equal(root.get("isbn"), searchParams.getIsbn()));
        }
        if(searchParams.getYearOfPublishment() != null){
            predicates.add(criteriaBuilder.equal(root.get("yearOfPublishment"), searchParams.getYearOfPublishment()));
        }

        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(query).getResultList();
    }
}
