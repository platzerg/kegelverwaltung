package platzerworld.kegeln.gui;

import platzerworld.kegeln.R;
import platzerworld.kegeln.common.ConstantsIF;
import platzerworld.kegeln.common.KeyValueVO;
import platzerworld.kegeln.common.style.StyleManager;
import platzerworld.kegeln.mannschaft.db.MannschaftSpeicher;
import platzerworld.kegeln.mannschaft.vo.MannschaftVO;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Zeigt die Liste der Geokontakte an.
 * 
 * @author Arno Becker, David Müller 2010 visionera gmbh
 * 
 */
public class MannschaftAnlegen extends Activity implements ConstantsIF{

	/** Kuerzel fuers Logging. */
	private static final String TAG = ErgebnisseAnzeigen.class.getSimpleName();
	
	private Button mSpeichernButton;
	private Button mAbbruchButton;

	private MannschaftSpeicher mMannschaftSpeicher;
	
	private MannschaftVO mMannschaftVO;
	
	private KeyValueVO klasseVO;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.d(TAG, "onCreate(): entered...");
		setContentView(R.layout.mannschaft_anlegen);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		klasseVO = (KeyValueVO) extras.getSerializable(INTENT_EXTRA_KLASSE);
		
		if (klasseVO != null) {
			Toast.makeText(this, "Mannschaft anlegen: " +klasseVO.value, Toast.LENGTH_SHORT).show();
		}

		init();
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onDestroy() {
		cleanDatabase();
		super.onDestroy();
	}
	
	private void init(){
		initStyle();
		initWidgets();
		initListener();
		initContextMenu();
		initDatabase();
	}
	
	private void initStyle() {
		Typeface font = StyleManager.getInstance().init(this).getTypeface();
		TextView titeltext = (TextView) findViewById(R.id.txt_mannschaft_neuanlegen_titel);
		titeltext.setTypeface(font);
	}
	
	private void initWidgets(){
		mSpeichernButton = (Button) findViewById(R.id.sf_mannschaft_neuanlagen_ok);
		mAbbruchButton = (Button) findViewById(R.id.sf_mannschaft_neuanlagen_abbruch);
	}
	
	private void initListener(){		
		mSpeichernButton.setOnClickListener(mMannschaftAnlegenOkListener);
		mAbbruchButton.setOnClickListener(mMannschaftAnlegenAbbruchListener);
	}
	
	private void initContextMenu(){
	}
	
	private void initDatabase(){
	}
	
	private void cleanDatabase(){
	}
	
	private OnClickListener mMannschaftAnlegenOkListener = new OnClickListener() {
	    public void onClick(View v) {	    	
	      speichern();
	    }
	};
	
	private OnClickListener mMannschaftAnlegenAbbruchListener = new OnClickListener() {
	    public void onClick(View v) {
	      finish();
	    }
	};
	
	private void speichern(){
		EditText name = (EditText) findViewById(R.id.edt_mannschaft_neuanlegen_name);
		mMannschaftSpeicher = new MannschaftSpeicher(this);		
		long klasseId = klasseVO.key;
		
		mMannschaftVO = new MannschaftVO(klasseId, name.getText().toString());
		mMannschaftVO.id = 0;
		mMannschaftSpeicher.speichereMannschaft(mMannschaftVO);
		
		finish();
	}

	@Override
	public void finish() {
		Intent data = new Intent();
		data.putExtra(INTENT_EXTRA_NEUE_MANNSCHAFT, mMannschaftVO);
		setResult(RESULT_OK, data);
		super.finish();
	}

}
