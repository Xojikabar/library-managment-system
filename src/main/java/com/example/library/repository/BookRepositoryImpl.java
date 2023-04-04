package com.example.library.repository;

import com.example.library.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl {

    @Autowired
    private EntityManager entityManager;

    public List<Book> universalSearch(Map<String, String> params) {
        String sql = "select b from Book b where 1=1 ";

        StringBuilder queryCondition = new StringBuilder();
        generateQuery(queryCondition, params);

        Query query = entityManager.createQuery(sql + queryCondition);
        setParams(query,params);

       return query.getResultList();
    }

    private void generateQuery(StringBuilder query, Map<String, String> map) {

        if (map.containsKey("name")) {
            query.append(" and upper(b.name) like :name");
        }

        if (map.containsKey("author")) {
            query.append(" and upper(b.author) like :author");
        }

        if (map.containsKey("amount")) {
            query.append(" and b.amount >= :amount");
        }

//        if(map.containsKey("genre")){
//            query.append(" and upper(b.genre) like :genre");
//        }
    }

    private void setParams(Query query,Map<String,String > map){
        if (map.containsKey("name")) {
            query.setParameter("name", "%" + map.get("name").toUpperCase() + "%");
        }
        if (map.containsKey("author")) {
            query.setParameter("author", "%" + map.get("author").toUpperCase() + "%");
        }
        if (map.containsKey("genre")) {
            query.setParameter("genre", "%" + map.get("genre").toUpperCase() + "%");
        }
        if (map.containsKey("amount")) {
            query.setParameter("amount", Integer.parseInt(map.get("amount")));
        }
    }


}
