package com.risk6441.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.risk6441.gameutilities.GameUtilitiesTestSuite;
import com.risk6441.maputilities.MapUtilitiesTestSuite;
import com.risk6441.models.ModelsTestSuite;

/**
 * @author Jemish
 *
 */
@RunWith(Suite.class)
@SuiteClasses({MapUtilitiesTestSuite.class,GameUtilitiesTestSuite.class, ModelsTestSuite.class})
public class MainTestSuite {
}

