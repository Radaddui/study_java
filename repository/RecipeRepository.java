package org.zerock.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.domain.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{
    ;;
} // end interface
