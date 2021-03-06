package com.itemis.maven.plugins.unleash.util.predicates;

import org.apache.maven.model.Plugin;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class IsSnapshotPluginTest {
  @DataProvider
  public static Object[][] plugins() {
    return new Object[][] { { createPlugin("1-SNAPSHOT"), true }, { createPlugin("1"), false },
        { createPlugin("1-snapshot"), true }, { createPlugin("1-Alpha-SNAPSHOT"), true },
        { createPlugin("1.alpha"), false }, { createPlugin(null), false } };
  }

  @Test
  @UseDataProvider("plugins")
  public void TestApply(Plugin p, boolean expected) {
    Assert.assertEquals(expected, IsSnapshotPlugin.INSTANCE.apply(p));
  }

  private static Plugin createPlugin(String version) {
    Plugin p = new Plugin();
    p.setVersion(version);
    return p;
  }
}
