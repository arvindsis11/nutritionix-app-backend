package com.nutritionix.whishlist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutritionix.whishlist.exception.InvalidCredentialsException;
import com.nutritionix.whishlist.feign.AuthClient;
import com.nutritionix.whishlist.model.WhishList;
import com.nutritionix.whishlist.service.WhishListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {WhishListController.class})
@ExtendWith(SpringExtension.class)
class WhishListControllerTest {
    @MockBean
    private AuthClient authClient;

    @Autowired
    private WhishListController whishListController;

    @MockBean
    private WhishListService whishListService;

    /**
     * Method under test:
     * {@link WhishListController#addFoodToWhishlist(WhishList, String)}
     */
    @Test
    void testAddFoodToWhishlist() throws Exception {
        Mockito.<ResponseEntity<?>>when(authClient.validateToken(Mockito.<String>any()))
                .thenThrow(new InvalidCredentialsException("An error occurred"));
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/v1.0/wishlist/addItem");
        postResult.characterEncoding("https://example.org/example");
        MockHttpServletRequestBuilder headerResult = postResult.header("Authorization",
                "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        WhishList whishList = new WhishList();
        whishList.setFoodName("Food Name");
        whishList.setId(1L);
        whishList.setLocale("en");
        whishList.setPhoto("alice.liddell@example.org");
        whishList.setServingQty(1);
        whishList.setServingUnit("Serving Unit");
        whishList.setTagId("42");
        whishList.setTagName("Tag Name");
        whishList.setUserId("42");
        String content = (new ObjectMapper()).writeValueAsString(whishList);
        MockHttpServletRequestBuilder requestBuilder = headerResult.contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(whishListController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    /**
     * Method under test:
     * {@link WhishListController#deleteFromWhishList(Long, String)}
     */
    @Test
    void testDeleteFromWhishList() throws Exception {
        Mockito.<ResponseEntity<?>>when(authClient.validateToken(Mockito.<String>any()))
                .thenThrow(new InvalidCredentialsException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1.0/wishlist/delete/{id}", "Uri Variables", "Uri Variables")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(whishListController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}
