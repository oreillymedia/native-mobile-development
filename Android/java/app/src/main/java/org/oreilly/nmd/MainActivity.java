package org.oreilly.nmd;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity {

  private CheckBox mTermsCheckbox;
  private Button mBrowseButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mTermsCheckbox = findViewById(R.id.terms_checkbox);
    mTermsCheckbox.setOnCheckedChangeListener(this::onCheckChanged);
    mBrowseButton = findViewById(R.id.browse_button);
    mBrowseButton.setOnClickListener(this::browseContent);
    SQLiteDatabase database = new DbHelper(this, "db", null, 1).getWritableDatabase();
    database.close();
  }

  private void onCheckChanged(Button button, boolean checked) {
    mBrowseButton.setEnabled(checked);
  }

  private void browseContent(View view) {
    Intent intent = new Intent(this, BrowseContentActivity.class);
    startActivity(intent);
  }

}
