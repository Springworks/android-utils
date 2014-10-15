package se.springworks.android.utils;

import android.content.Context;
import com.google.inject.AbstractModule;
import se.springworks.android.utils.persistence.IKeyValueStorage;
import se.springworks.android.utils.persistence.SharedPreferencesStorage;
import se.springworks.javautil.json.IJsonParser;
import se.springworks.javautil.json.JacksonParser;

public class MockModule extends AbstractModule {

  private Context context;

  public MockModule(Context context) {
    this.context = context;
  }

  @Override
  protected void configure() {
    bind(Context.class).toInstance(context);
    bind(IKeyValueStorage.class).to(SharedPreferencesStorage.class);
    bind(IJsonParser.class).to(JacksonParser.class);
  }
}
