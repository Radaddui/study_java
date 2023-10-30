package org.zerock.myapp.service;

import org.zerock.myapp.domain.RecipeDTO;

public interface RecipeService {

    public abstract void recipeWrite(RecipeDTO recipeDTO, String LoggedInUserId);



} // end interface