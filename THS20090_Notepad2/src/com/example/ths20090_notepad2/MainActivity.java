package com.example.ths20090_notepad2;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.text.Selection;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
private String m_dataName = "Memo1";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//読み込み処理を書く
		SharedPreferences pref = getSharedPreferences("Memo1",
								MODE_PRIVATE);
		String memo = pref.getString(
					"memo", "");
		int cursor = pref.getInt(
						"cursor",0);
		EditText edit = (EditText)findViewById(
					R.id.editText1
				);
		edit.setText(memo);
		edit.setSelection(cursor);

		load();
	}
	
	private void load()
	{
		//テキスト情報の読み込み
		EditText edit = (EditText)findViewById(R.id.editText1);
		SharedPreferences pref = getSharedPreferences(m_dataName,MODE_PRIVATE);
		edit.setText(pref.getString("memo",""));
		edit.setSelection(pref.getInt("cursor",0));
		//ラベル表示
		TextView text = (TextView)findViewById(R.id.TextView1);
		text.setText(m_dataName);
	}
	
	private void save()
	{
		//テキスト情報の保存
		EditText edit = (EditText)findViewById(R.id.editText1);
		SharedPreferences pref = getSharedPreferences(m_dataName,MODE_PRIVATE);
		SharedPreferences.Editor editor =pref.edit();
		editor.putString("memo",edit.getText().toString()); //テキストの保存
		editor.putInt("cursor", Selection.getSelectionStart(edit.getText())); //カーソル位置の保存
		editor.commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		//今のものを保存
		save();
		
		int id = item.getItemId();
		
		//データ変更
		String name = "";
		switch(id){
		case R.id.memo1:name = "Memo1"; break;
		case R.id.memo2:name = "Memo2"; break;
		case R.id.memo3:name = "Memo3"; break;
		}
		
		// ここでロード
		SharedPreferences pref = 
			getSharedPreferences(
				name, MODE_PRIVATE);
		
		//ロード
		m_dataName = name;
		load();
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStop() {
		save();
		
		//保存処理を書く
		Log.d("test","save text In onstop");
		EditText edit = (EditText)findViewById(
					R.id.editText1
				);
		SharedPreferences pref = getSharedPreferences("Memo1",MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		
		editor.putString("memo",
				edit.getText().toString());
		
		int cursor = Selection.getSelectionStart(
				edit.getText());
		
		editor.putInt("cursor",cursor);
		editor.commit();
		
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
