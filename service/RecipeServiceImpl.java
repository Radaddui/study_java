package org.zerock.myapp.service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.Categories;
import org.zerock.myapp.domain.Recipe;
import org.zerock.myapp.domain.RecipeDTO;
import org.zerock.myapp.domain.Users;
import org.zerock.myapp.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@NoArgsConstructor

@Service
public class RecipeServiceImpl implements RecipeService{

    @Setter(onMethod_ = @Autowired)
    private RecipeRepository recipeRepo;

    @Setter(onMethod_ = @Autowired)
    private UsersService usersService;

    @Setter(onMethod_ = @Autowired)
    private CategoriesService categoriesService;


    @Override
    public void recipeWrite(RecipeDTO recipeDTO, String LoggedInUserId) {

        Users loggedInUserId =  usersService.getUserById(LoggedInUserId);

        if(loggedInUserId == null){
            ;;
            return;
        }

        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDTO.getTitle());
        recipe.setContent(recipeDTO.getContent());

        // 로그인 된 유저 ID값을 불러온다.
        recipe.setFk_users(loggedInUserId);

        // 카테고리 처리
        List<String> selectedCategories = recipeDTO.getCategories();
        List<Categories> categoryEntities = new ArrayList<>(); // List로 변경

        for (String categoryName : selectedCategories) {
            Categories categories = categoriesService.getCategoryByName(categoryName);
            if (categories != null) {
                categoryEntities.add(categories);
            }
        }


        recipe.setCategories(categoryEntities); // List<Categories>로 설정

        recipe.setFk_Categories(categoryEntities.get(0));

        recipeRepo.save(recipe);


    } // recipeWrite
} // end class
