package com.risk6441.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.risk6441.gameutilities.GameUtilitiesTestSuite;
import com.risk6441.maputilities.MapUtilitiesTestSuite;
import com.risk6441.models.ModelsTestSuite;
import com.risk6441.strategy.StrategyTestSuite;

/**
 * @author Jemish
 *
 */
@RunWith(Suite.class)
@SuiteClasses({MapUtilitiesTestSuite.class,GameUtilitiesTestSuite.class, ModelsTestSuite.class, StrategyTestSuite.class})
public class MainTestSuite {
}

