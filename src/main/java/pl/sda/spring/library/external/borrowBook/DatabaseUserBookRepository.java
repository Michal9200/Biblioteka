package pl.sda.spring.library.external.borrowBook;


public class DatabaseUserBookRepository {

    private JpaUserBookRepository jpaUserBookRepository;


    public void create(UserBookEntity userBookEntity) {
        jpaUserBookRepository.save(userBookEntity);
    }

}
