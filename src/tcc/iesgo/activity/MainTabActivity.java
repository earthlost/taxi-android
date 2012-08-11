package tcc.iesgo.activity;


import java.util.Locale;

import tcc.iesgo.activity.R;
import tcc.iesgo.persistence.SQLiteAdapter;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TabHost;

public class MainTabActivity extends TabActivity {
	
	public static TabHost tabHost;
	
	SQLiteAdapter mySQLiteAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mySQLiteAdapter = new SQLiteAdapter(getApplicationContext());
        mySQLiteAdapter.openToRead(); //Abre para leitura

        Cursor cursor = mySQLiteAdapter.queueAll(); //Dados salvos no banco
        startManagingCursor(cursor);

        if(cursor.getCount() > 0){ //Caso seja encontrado algum registro
        	
        	cursor.moveToFirst(); //Move para o primeiro registro do cursor
        	
        	//Salva o idioma do usuário
	        String lang = cursor.getString(cursor.getColumnIndex(SQLiteAdapter.KEY_LANG));
	     
	        //Seleciona o idioma do usuário internamente (pt, es, en)
	        Locale appLoc = new Locale(lang);
	        Locale.setDefault(appLoc);
	        
	        Configuration appConfig = new Configuration();
	        appConfig.locale = appLoc;
	        getBaseContext().getResources().updateConfiguration(appConfig,
	        		getBaseContext().getResources().getDisplayMetrics()); //Efetua a alteração
        }		
        mySQLiteAdapter.close(); //Fecha a conexão
        
    	setContentView(R.layout.tab_main); //Seta o layout da activity

		Resources res = getResources(); 
		tabHost = getTabHost();
		TabHost.TabSpec spec;
		
		Intent intentAndroid = new Intent().setClass(this, ClientMapActivity.class);
		spec = tabHost
				.newTabSpec(getString(R.string.menu_mapa))
				.setIndicator(getString(R.string.menu_mapa), res.getDrawable(R.layout.tab_icon_map))
				.setContent(intentAndroid);
		tabHost.addTab(spec);
		/*

		intentAndroid = new Intent().setClass(this, TabGroupReservaActivity.class);
		spec = tabHost
			.newTabSpec(getString(R.string.menu_reservar))
			.setIndicator(getString(R.string.menu_reservar), res.getDrawable(R.layout.tab_icon_lista))
			.setContent(intentAndroid);
		tabHost.addTab(spec);
		
		
		intentAndroid = new Intent().setClass(this, TabGroupMinhasReservasActivity.class);
		spec = tabHost
			.newTabSpec(getString(R.string.menu_m_reservas))
			.setIndicator(getString(R.string.menu_m_reservas), res.getDrawable(R.layout.tab_icon_m_reservas))
			.setContent(intentAndroid);
		tabHost.addTab(spec);
		
		
		intentAndroid = new Intent().setClass(this, SobreActivity.class);
		spec = tabHost
			.newTabSpec(getString(R.string.menu_sobre))
			.setIndicator(getString(R.string.menu_sobre), res.getDrawable(R.layout.tab_icon_sobre))
			.setContent(intentAndroid);
		tabHost.addTab(spec);
		*/
		tabHost.setCurrentTab(0);
	}
	
	//Chamado quando a activity não é visível para o usuário
	@Override
	protected void onStop() {
		super.onStop();	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mySQLiteAdapter = new SQLiteAdapter(getApplicationContext());
        mySQLiteAdapter.openToRead();

        Cursor cursor = mySQLiteAdapter.queueAll();
        startManagingCursor(cursor);
        
        
        if(cursor.getCount() > 0){
	        cursor.moveToFirst();
	
	        String lang = cursor.getString(cursor.getColumnIndex(SQLiteAdapter.KEY_LANG));
	     
	        // Seleciona a linguagem do usuário
	        Locale appLoc = new Locale(lang);
	            
	        Locale.setDefault(appLoc);
	        Configuration appConfig = new Configuration();
	        appConfig.locale = appLoc;
	        getBaseContext().getResources().updateConfiguration(appConfig,
	             getBaseContext().getResources().getDisplayMetrics());
        }		
        mySQLiteAdapter.close();
        
	}
	
	/*	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {     
	    menu.add(0,1,0,"OK");
	    return true;
	}
	*/
}