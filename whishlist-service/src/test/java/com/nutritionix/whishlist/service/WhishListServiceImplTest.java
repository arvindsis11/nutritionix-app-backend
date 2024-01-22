package com.nutritionix.whishlist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nutritionix.whishlist.exception.ResourceAlreadyExistsException;
import com.nutritionix.whishlist.exception.ResourceNotFoundException;
import com.nutritionix.whishlist.model.WhishList;
import com.nutritionix.whishlist.repo.WhishListRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WhishListServiceImpl.class})
@ExtendWith(SpringExtension.class)
class WhishListServiceImplTest {
    @MockBean
    private WhishListRepo whishListRepo;

    @Autowired
    private WhishListServiceImpl whishListServiceImpl;

    /**
     * Method under test: {@link WhishListServiceImpl#getWhishlistByUserId(String)}
     */
    @Test
    void testGetWhishlistByUserId() {
        when(whishListRepo.findByUserId(Mockito.<String>any())).thenReturn(new ArrayList<>());
        assertThrows(ResourceNotFoundException.class, () -> whishListServiceImpl.getWhishlistByUserId("42"));
        verify(whishListRepo).findByUserId(Mockito.<String>any());
    }

    /**
     * Method under test: {@link WhishListServiceImpl#getWhishlistByUserId(String)}
     */
    @Test
    void testGetWhishlistByUserId2() {
        WhishList whishList = new WhishList();
        whishList.setFoodName("item with given id not found");
        whishList.setId(1L);
        whishList.setLocale("en");
        whishList.setPhoto("alice.liddell@example.org");
        whishList.setServingQty(1);
        whishList.setServingUnit("item with given id not found");
        whishList.setTagId("42");
        whishList.setTagName("item with given id not found");
        whishList.setUserId("42");

        ArrayList<WhishList> whishListList = new ArrayList<>();
        whishListList.add(whishList);
        when(whishListRepo.findByUserId(Mockito.<String>any())).thenReturn(whishListList);
        List<WhishList> actualWhishlistByUserId = whishListServiceImpl.getWhishlistByUserId("42");
        verify(whishListRepo).findByUserId(Mockito.<String>any());
        assertEquals(1, actualWhishlistByUserId.size());
        assertSame(whishListList, actualWhishlistByUserId);
    }

    /**
     * Method under test: {@link WhishListServiceImpl#getWhishlistByUserId(String)}
     */
    @Test
    void testGetWhishlistByUserId3() {
        when(whishListRepo.findByUserId(Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> whishListServiceImpl.getWhishlistByUserId("42"));
        verify(whishListRepo).findByUserId(Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link WhishListServiceImpl#addFoodItemToWhishlist(WhishList)}
     */
    @Test
    void testAddFoodItemToWhishlist() {
        when(whishListRepo.existsByTagId(Mockito.<String>any())).thenReturn(true);

        WhishList whishlist = new WhishList();
        whishlist.setFoodName("Food Name");
        whishlist.setId(1L);
        whishlist.setLocale("en");
        whishlist.setPhoto("alice.liddell@example.org");
        whishlist.setServingQty(1);
        whishlist.setServingUnit("Serving Unit");
        whishlist.setTagId("42");
        whishlist.setTagName("Tag Name");
        whishlist.setUserId("42");
        assertThrows(ResourceAlreadyExistsException.class, () -> whishListServiceImpl.addFoodItemToWhishlist(whishlist));
        verify(whishListRepo).existsByTagId(Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link WhishListServiceImpl#addFoodItemToWhishlist(WhishList)}
     */
    @Test
    void testAddFoodItemToWhishlist2() {
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
        when(whishListRepo.existsByTagId(Mockito.<String>any())).thenReturn(false);
        when(whishListRepo.save(Mockito.<WhishList>any())).thenReturn(whishList);

        WhishList whishlist = new WhishList();
        whishlist.setFoodName("Food Name");
        whishlist.setId(1L);
        whishlist.setLocale("en");
        whishlist.setPhoto("alice.liddell@example.org");
        whishlist.setServingQty(1);
        whishlist.setServingUnit("Serving Unit");
        whishlist.setTagId("42");
        whishlist.setTagName("Tag Name");
        whishlist.setUserId("42");
        ResponseEntity<?> actualAddFoodItemToWhishlistResult = whishListServiceImpl.addFoodItemToWhishlist(whishlist);
        verify(whishListRepo).existsByTagId(Mockito.<String>any());
        verify(whishListRepo).save(Mockito.<WhishList>any());
        assertEquals(201, actualAddFoodItemToWhishlistResult.getStatusCodeValue());
        assertTrue(actualAddFoodItemToWhishlistResult.hasBody());
        assertTrue(actualAddFoodItemToWhishlistResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link WhishListServiceImpl#addFoodItemToWhishlist(WhishList)}
     */
    @Test
    void testAddFoodItemToWhishlist3() {
        when(whishListRepo.existsByTagId(Mockito.<String>any()))
                .thenThrow(new ResourceAlreadyExistsException("An error occurred"));

        WhishList whishlist = new WhishList();
        whishlist.setFoodName("Food Name");
        whishlist.setId(1L);
        whishlist.setLocale("en");
        whishlist.setPhoto("alice.liddell@example.org");
        whishlist.setServingQty(1);
        whishlist.setServingUnit("Serving Unit");
        whishlist.setTagId("42");
        whishlist.setTagName("Tag Name");
        whishlist.setUserId("42");
        assertThrows(ResourceAlreadyExistsException.class, () -> whishListServiceImpl.addFoodItemToWhishlist(whishlist));
        verify(whishListRepo).existsByTagId(Mockito.<String>any());
    }

    /**
     * Method under test: {@link WhishListServiceImpl#deleteFromWhishList(Long)}
     */
    @Test
    void testDeleteFromWhishList() {
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
        Optional<WhishList> ofResult = Optional.of(whishList);
        doNothing().when(whishListRepo).deleteById(Mockito.<Long>any());
        when(whishListRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ResponseEntity<?> actualDeleteFromWhishListResult = whishListServiceImpl.deleteFromWhishList(1L);
        verify(whishListRepo).deleteById(Mockito.<Long>any());
        verify(whishListRepo).findById(Mockito.<Long>any());
        assertEquals("item deleted successfully", actualDeleteFromWhishListResult.getBody());
        assertEquals(200, actualDeleteFromWhishListResult.getStatusCodeValue());
        assertTrue(actualDeleteFromWhishListResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link WhishListServiceImpl#deleteFromWhishList(Long)}
     */
    @Test
    void testDeleteFromWhishList2() {
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
        Optional<WhishList> ofResult = Optional.of(whishList);
        doThrow(new ResourceNotFoundException("An error occurred")).when(whishListRepo).deleteById(Mockito.<Long>any());
        when(whishListRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ResourceNotFoundException.class, () -> whishListServiceImpl.deleteFromWhishList(1L));
        verify(whishListRepo).deleteById(Mockito.<Long>any());
        verify(whishListRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link WhishListServiceImpl#deleteFromWhishList(Long)}
     */
    @Test
    void testDeleteFromWhishList3() {
        Optional<WhishList> emptyResult = Optional.empty();
        when(whishListRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class, () -> whishListServiceImpl.deleteFromWhishList(1L));
        verify(whishListRepo).findById(Mockito.<Long>any());
    }
}
