package platzerworld.kegeln.gui.beta;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;

public class TestingData extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        SQLiteDatabase db;
        
        db = openOrCreateDatabase(
       		"TestingData.db"
       		, SQLiteDatabase.CREATE_IF_NECESSARY
       		, null
       		);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        
        final String CREATE_TABLE_COUNTRIES =
        	"CREATE TABLE tbl_countries ("
        	+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
        	+ "country_name TEXT);";
        final String CREATE_TABLE_STATES = 
        	"CREATE TABLE tbl_states ("
        	+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
        	+ "state_name TEXT,"
        	+ "country_id INTEGER NOT NULL CONSTRAINT "
        	+ "contry_id REFERENCES tbl_contries(id) "
        	+ "ON DELETE CASCADE);";
        db.execSQL(CREATE_TABLE_COUNTRIES);
        db.execSQL(CREATE_TABLE_STATES);
        /*
        final String CREATE_TRIGGER_STATES = 
        	"CREATE TRIGGER fk_insert_state BEFORE "
        	+ "INSERT on tbl_states"
        	+ "FOR EACH ROW "
        	+ "BEGIN "
        	+ "SELECT RAISE(ROLLBACK, 'insert on table "
        	+ ""tbl_states" voilates foreign key constraint "
        	+ ""fk_insert_state"') WHERE (SELECT id FROM "
        	+ "tbl_countries WHERE id = NEW.country_id) IS NULL; "
        	+ "END;";
        db.execSQL(CREATE_TRIGGER_STATES);
        */
    }
}