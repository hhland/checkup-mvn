/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.lenskit.test;

import java.io.File;
import java.util.List;
import org.grouplens.lenskit.ItemRecommender;
import org.grouplens.lenskit.ItemScorer;
import org.grouplens.lenskit.RatingPredictor;
import org.grouplens.lenskit.RecommenderBuildException;
import org.grouplens.lenskit.baseline.BaselineScorer;
import org.grouplens.lenskit.baseline.ItemMeanRatingItemScorer;
import org.grouplens.lenskit.baseline.UserMeanBaseline;
import org.grouplens.lenskit.baseline.UserMeanItemScorer;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.core.LenskitRecommender;
import org.grouplens.lenskit.data.dao.EventDAO;
import org.grouplens.lenskit.data.dao.SimpleFileRatingDAO;
import org.grouplens.lenskit.knn.item.ItemItemScorer;
import org.grouplens.lenskit.scored.ScoredId;
import org.grouplens.lenskit.transform.normalize.BaselineSubtractingUserVectorNormalizer;
import org.grouplens.lenskit.transform.normalize.UserVectorNormalizer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lin
 */
public class ExampleTest {

    LenskitConfiguration config;

    public ExampleTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        config=new LenskitConfiguration();
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void getStart() throws RecommenderBuildException {

// Use item-item CF to score items
        config.bind(ItemScorer.class)
                .to(ItemItemScorer.class);
// let's use personalized mean rating as the baseline/fallback predictor.
// 2-step process:
// First, use the user mean rating as the baseline scorer
        config.bind(BaselineScorer.class, ItemScorer.class)
                .to(UserMeanItemScorer.class);
// Second, use the item mean rating as the base for user means
        config.bind(UserMeanBaseline.class, ItemScorer.class)
                .to(ItemMeanRatingItemScorer.class);
// and normalize ratings by baseline prior to computing similarities
        config.bind(UserVectorNormalizer.class)
                .to(BaselineSubtractingUserVectorNormalizer.class);
        
        config.bind(EventDAO.class).to(new SimpleFileRatingDAO(
                new File(ClassLoader.getSystemResource("ratings.txt").getFile()), ","));
        LenskitRecommender rec = LenskitRecommender.build(config);
        ItemRecommender irec = rec.getItemRecommender();
        List<ScoredId> recommendations = irec.recommend(42, 10);
        
        RatingPredictor pred = rec.getRatingPredictor();
       double score = pred.predict(42, 17);
    }
}
